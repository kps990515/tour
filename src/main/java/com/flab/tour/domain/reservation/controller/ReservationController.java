package com.flab.tour.domain.reservation.controller;

import com.flab.tour.common.api.Api;
import com.flab.tour.common.error.ReservationErrorCode;
import com.flab.tour.common.exception.ApiException;
import com.flab.tour.domain.reservation.controller.model.ReservationRequest;
import com.flab.tour.domain.reservation.controller.model.ReservationSearchRequest;
import com.flab.tour.domain.reservation.controller.model.ReservationSearchResponse;
import com.flab.tour.domain.reservation.service.ReservationService;
import com.flab.tour.domain.user.controller.model.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/reservations/")
public class ReservationController {
    private final ReservationService reservationService;

    @GetMapping("")
    public Api<List<ReservationSearchResponse>> searchAllReservation(User user, @Valid @RequestBody ReservationSearchRequest request){
        request.validateDates();
        var response = reservationService.searchAllReservations(user, request);
        return Api.OK(response);
    }

    @PostMapping("/pessimisticReservation")
    public Api<String> pessimisticReservate(User user, @Valid @RequestBody ReservationRequest request){
        boolean success = reservationService.pessimisticReservate(user, request);
        if(!success){
            throw new ApiException(ReservationErrorCode.OUT_OF_QUANTITY);
        }else{
            return Api.OK("예약완료");
        }
    }

    @PostMapping("/optimisticReservation")
    public Api<String> optimisticReservate(User user, @Valid @RequestBody ReservationRequest request){
        boolean success = reservationService.optimisticReservate(user, request);
        if(!success){
            throw new ApiException(ReservationErrorCode.OUT_OF_QUANTITY);
        }else{
            return Api.OK("예약완료");
        }
    }
}
