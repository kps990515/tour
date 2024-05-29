package com.flab.tour.domain.reservation.controller.model;

import com.flab.tour.common.error.ReservationErrorCode;
import com.flab.tour.common.exception.ApiException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReservationSearchRequest {
    private String status;
    private String startDate;
    private String endDate;

    public void validateDates(){
        if (startDate == null || startDate.length() != 8) {
            throw new ApiException(ReservationErrorCode.INVALID_DATE);
        }
        if (endDate == null || endDate.length() != 8) {
            throw new ApiException(ReservationErrorCode.INVALID_DATE);
        }
    }
}
