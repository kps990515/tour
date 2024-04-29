package com.flab.tour.common.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum TokenErrorCode implements BaseErrorCode {
    INVALID_TOKEN(400, 2000, "유효하지 않은 토큰"),
    EXPIRED_TOKEN(400, 2001, "만료된 토큰"),
    TOKEN_EXCEPTION(400, 2002, "알수없는 토큰 에러"),
    TOKEN_NOT_FOUND(400, 2003, "토큰 없음");

    private final Integer httpStatusCode;
    private final Integer errorCode;
    private final String description;
}
