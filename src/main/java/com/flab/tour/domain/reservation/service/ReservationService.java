package com.flab.tour.domain.reservation.service;

import com.flab.tour.config.objectmapper.BaseService;
import com.flab.tour.db.product.ProductAvailabilityEntity;
import com.flab.tour.db.product.ProductRepository;
import com.flab.tour.db.reservation.ReservationMapper;
import com.flab.tour.db.reservation.ReservationRepository;
import com.flab.tour.domain.reservation.controller.model.ReservationRequest;
import com.flab.tour.domain.reservation.controller.model.ReservationSearchRequest;
import com.flab.tour.domain.reservation.controller.model.ReservationSearchResponse;
import com.flab.tour.domain.user.controller.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ReservationService extends BaseService {
    private final ReservationRepository reservationRepository;
    private final ProductRepository productRepository;
    private final ReservationMapper reservationMapper;

    public List<ReservationSearchResponse> searchAllReservations(User user, ReservationSearchRequest request) {
        var startDate = convertDate(request.getStartDate());
        var endDate = convertDate(request.getEndDate());

        return reservationRepository.findAllReservations(user.getUserId(), startDate, endDate, request.getStatus());
    }

    @Transactional
    public boolean pessimisticReservate(User user, ReservationRequest request) {
        // Pessimistic Lock 설정
        ProductAvailabilityEntity productAvailabilityEntity = reservationRepository.lockProduct(request.getProductId());

        var startDate = convertDate(request.getReservationDate());
        int reservationNumber = reservationRepository.pessimisticReservate(request.getProductId(), request.getQuantity());
        return reservationNumber > 0;
    }

    @Transactional
    public boolean optimisticReservate(User user, ReservationRequest request) {
        boolean success = false;
        int retryCount = 3;

        while (!success && retryCount > 0) {
            try {
                ProductAvailabilityEntity productAvailability = productRepository.findById(request.getProductId()).orElseThrow();
                int version = productAvailability.getVersion();

                int reservationNumber = reservationRepository.optimistcReservate(request.getProductId(), request.getQuantity(), version);
                success = reservationNumber > 0;
            } catch (OptimisticLockingFailureException e) {
                retryCount--;
            }
        }

        return success;
    }

    public LocalDate convertDate(String date){
        return LocalDate.parse(date);
    }
}
