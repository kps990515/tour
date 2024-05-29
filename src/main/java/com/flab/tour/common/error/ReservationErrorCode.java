package com.flab.tour.common.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ReservationErrorCode implements BaseErrorCode {
    INVALID_DATE(400, 2001, "잘못된 날짜형식");

    private final Integer httpStatusCode;
    private final Integer errorCode;
    private final String description;
}
