package com.flab.tour.domain.reservation.controller.model;

import com.flab.tour.common.error.ReservationErrorCode;
import com.flab.tour.common.exception.ApiException;
import com.flab.tour.common.validation.Date;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReservationSearchRequest {
    private String status;

    @Date
    @NotBlank
    private String startDate;

    @Date
    @NotBlank
    private String endDate;

    public void validateDates(){ //@Validation으로 바꾸기
        if (startDate == null || startDate.length() != 8) {
            throw new ApiException(ReservationErrorCode.INVALID_DATE);
        }
        if (endDate == null || endDate.length() != 8) {
            throw new ApiException(ReservationErrorCode.INVALID_DATE);
        }
    }
}
