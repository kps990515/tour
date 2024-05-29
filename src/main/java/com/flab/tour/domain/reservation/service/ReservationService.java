package com.flab.tour.domain.reservation.service;

import com.flab.tour.config.objectmapper.BaseService;
import com.flab.tour.db.reservation.ReservationMapper;
import com.flab.tour.db.reservation.ReservationRepository;
import com.flab.tour.domain.reservation.controller.model.ReservationSearchRequest;
import com.flab.tour.domain.reservation.controller.model.ReservationSearchResponse;
import com.flab.tour.domain.user.controller.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ReservationService extends BaseService {
    private final ReservationRepository reservationRepository;
    private final ReservationMapper reservationMapper;

    public List<ReservationSearchResponse> searchAllReservations(User user, ReservationSearchRequest request) {
        var startDate = convertDate(request.getStartDate());
        var endDate = convertDate(request.getEndDate());

        return reservationRepository.findAllReservations(user.getUserId(), startDate, endDate, request.getStatus());
    }

    public LocalDate convertDate(String date){
        return LocalDate.parse(date);
    }
}
