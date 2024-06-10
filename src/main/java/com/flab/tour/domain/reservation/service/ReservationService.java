package com.flab.tour.domain.reservation.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.flab.tour.config.objectmapper.BaseService;
import com.flab.tour.db.product.ProductAvailabilityEntity;
import com.flab.tour.db.product.ProductRepository;
import com.flab.tour.db.reservation.ReservationEntity;
import com.flab.tour.db.reservation.ReservationMapper;
import com.flab.tour.db.reservation.ReservationRepository;
import com.flab.tour.domain.reservation.controller.model.ReservationRequest;
import com.flab.tour.domain.reservation.controller.model.ReservationSearchRequest;
import com.flab.tour.domain.reservation.controller.model.ReservationSearchResponse;
import com.flab.tour.domain.user.controller.model.User;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ReservationService extends BaseService {
    private final ReservationRepository reservationRepository;
    private final ProductRepository productRepository;
    private final RedisTemplate<String, Object> redisTemplate;
    private final ReservationMapper reservationMapper;
    private final ObjectMapper objectMapper;


    @Cacheable(value = "reservations", key = "#user.userId")
    @CircuitBreaker(name = "reservationService", fallbackMethod = "fallbackSearchAllReservations")
    public List<ReservationSearchResponse> searchAllReservations(User user, ReservationSearchRequest request) {
        var startDate = convertDate(request.getStartDate());
        var endDate = convertDate(request.getEndDate());

        return reservationRepository.findAllReservations(user.getUserId(), startDate, endDate, request.getStatus());
    }

    public List<ReservationSearchResponse> fallbackSearchAllReservations(User user, ReservationSearchRequest request) {
        // Redis 장애 발생 시 데이터베이스에서 직접 데이터를 조회
        var startDate = convertDate(request.getStartDate());
        var endDate = convertDate(request.getEndDate());
        return reservationRepository.findAllReservations(user.getUserId(), startDate, endDate, request.getStatus());
    }

    @Transactional
    public boolean pessimisticReservate(User user, ReservationRequest request) {
        // Pessimistic Lock 설정
        ProductAvailabilityEntity productAvailabilityEntity = productRepository.lockProduct(request.getProductId());

        var reservationDate = convertDate(request.getReservationDate());
        int reservationNumber = productRepository.pessimisticReservate(request.getProductId(), reservationDate, request.getQuantity());
        if (reservationNumber > 0) {
            // 1. 예약테이블에 데이터 Insert
            ReservationEntity reservationEntity = reservationMapper.toNewReservation(user, request);
            reservationRepository.save(reservationEntity);

            // 2. Redis에 데이터 Update
            updateReservationCache(user, request, reservationDate);
            return true;
        }
        return false;
    }

    @Transactional
    public boolean optimisticReservate(User user, ReservationRequest request) {
        boolean success = false;
        int retryCount = 3;
        var reservationDate = convertDate(request.getReservationDate());

        while (!success && retryCount > 0) {
            try {
                ProductAvailabilityEntity productAvailability = productRepository.findById(request.getProductId()).orElseThrow();
                int version = productAvailability.getVersion();

                int reservationNumber = productRepository.optimistcReservate(request.getProductId(), reservationDate, request.getQuantity(), version);
                if (reservationNumber > 0) {
                    success = true;

                    // 1. 예약테이블에 데이터 Insert
                    ReservationEntity reservationEntity = reservationMapper.toNewReservation(user, request);
                    reservationRepository.save(reservationEntity);

                    // 2. Redis에 데이터 Update
                    updateReservationCache(user, request, reservationDate);
                    return success;
                }
            } catch (OptimisticLockingFailureException e) {
                retryCount--;
            }
        }

        return success;
    }

    private void updateReservationCache(User user, ReservationRequest request, LocalDate reservationDate) {
        String cacheKey = "reservations:" + user.getUserId();

        // 1. redis 기존 데이터 조회
        Object cachedResult = redisTemplate.opsForValue().get(cacheKey);
        List<ReservationSearchResponse> reservationList;
        if (cachedResult != null) {
            reservationList = objectMapper.convertValue(cachedResult, new TypeReference<>() {});
        } else {
            reservationList = reservationRepository.findAllReservations(user.getUserId(), reservationDate, reservationDate, "reserved");
        }

        // 2. 예약 데이터 추가
        ReservationSearchResponse newReservation = copyVO(request, ReservationSearchResponse.class);
        newReservation.setReservationDate(reservationDate);

        reservationList.add(newReservation);

        // 3. 레디스 데이터 업데이트
        redisTemplate.opsForValue().set(cacheKey, reservationList, Duration.ofMinutes(10));
    }

    public LocalDate convertDate(String date){
        return LocalDate.parse(date);
    }
}
