package com.flab.tour.domain.reservation.controller;

import com.flab.tour.common.api.Api;
import com.flab.tour.domain.reservation.controller.model.ReservationSearchRequest;
import com.flab.tour.domain.reservation.controller.model.ReservationSearchResponse;
import com.flab.tour.domain.reservation.service.ReservationService;
import com.flab.tour.domain.user.controller.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/reservations/")
public class ReservationController {
    private final ReservationService reservationService;

    @GetMapping("/all")
    public Api<List<ReservationSearchResponse>> searchAllReservation(User user, @RequestBody ReservationSearchRequest request){
        request.validateDates();
        var response = reservationService.searchAllReservations(user, request);
        return Api.OK(response);
    }
}
