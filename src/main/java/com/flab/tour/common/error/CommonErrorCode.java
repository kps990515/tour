package com.flab.tour.common.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum CommonErrorCode implements BaseErrorCode {
    OK(200, 200, "성공"),
    BAD_REQUEST(HttpStatus.BAD_REQUEST.value(), 400, "잘못된 요청"),
    UNAUTHORIZED(HttpStatus.BAD_REQUEST.value(), 401, "인증실패"),
    SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR.value(), 500, "서버 에러"),
    NULL_POINT(HttpStatus.INTERNAL_SERVER_ERROR.value(), 512, "Null Point");

    // Enum 인스턴스 내부 값들을 변경 불가능하게 만들기 위해 final선언
    private final Integer httpStatusCode;
    private final Integer errorCode;
    private final String description;
}
