package com.flab.tour.common.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum UserErrorCode implements BaseErrorCode {
    USER_NOT_FOUND(400, 1001, "사용자를 찾을 수 없음");

    private final Integer httpStatusCode;
    private final Integer errorCode;
    private final String description;
}
