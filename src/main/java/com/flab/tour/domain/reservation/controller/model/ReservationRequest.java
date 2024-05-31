package com.flab.tour.domain.reservation.controller.model;

import com.flab.tour.common.error.ReservationErrorCode;
import com.flab.tour.common.exception.ApiException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReservationRequest {
    private String productId;
    private String reservationDate;
    private int quantity;

    public void validateDates(){
        if (reservationDate == null || reservationDate.length() != 8) {
            throw new ApiException(ReservationErrorCode.INVALID_DATE);
        }
    }
}
