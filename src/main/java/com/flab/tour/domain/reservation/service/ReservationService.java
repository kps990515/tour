package com.flab.tour.domain.reservation.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.flab.tour.config.objectmapper.BaseService;
import com.flab.tour.db.product.ProductAvailabilityEntity;
import com.flab.tour.db.product.ProductRepository;
import com.flab.tour.db.reservation.ReservationRepository;
import com.flab.tour.domain.reservation.controller.model.ReservationRequest;
import com.flab.tour.domain.reservation.controller.model.ReservationSearchRequest;
import com.flab.tour.domain.reservation.controller.model.ReservationSearchResponse;
import com.flab.tour.domain.user.controller.model.User;
import lombok.RequiredArgsConstructor;
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
    private final ObjectMapper objectMapper;


    public List<ReservationSearchResponse> searchAllReservations(User user, ReservationSearchRequest request) {
        var startDate = convertDate(request.getStartDate());
        var endDate = convertDate(request.getEndDate());
        String cacheKey = "reservations:" + user.getUserId();

        // 1. redis 조회
        Object cachedResult = redisTemplate.opsForValue().get(cacheKey);
        if (cachedResult != null) {
            return objectMapper.convertValue(cachedResult, new TypeReference<>() {});
        }

        // 2. redis에 없으면 DB에서 조회
        List<ReservationSearchResponse> reservationList = reservationRepository.findAllReservations(user.getUserId(), startDate, endDate, request.getStatus());

        // 3. redis에 저장
        redisTemplate.opsForValue().set(cacheKey, reservationList, Duration.ofMinutes(10));

        return reservationList;
    }

    @Transactional
    public boolean pessimisticReservate(User user, ReservationRequest request) {
        // Pessimistic Lock 설정
        ProductAvailabilityEntity productAvailabilityEntity = reservationRepository.lockProduct(request.getProductId());

        var reservationDate = convertDate(request.getReservationDate());
        int reservationNumber = reservationRepository.pessimisticReservate(request.getProductId(), reservationDate, request.getQuantity());
        if (reservationNumber > 0) {
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

                int reservationNumber = reservationRepository.optimistcReservate(request.getProductId(), reservationDate, request.getQuantity(), version);
                if (reservationNumber > 0) {
                    success = true;
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
