package com.flab.tour.common.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum UserErrorCode implements BaseErrorCode {
    USER_NOT_FOUND(400, 1001, "사용자를 찾을 수 없음"),
    REGISTER_FAIL(400, 1002, "가입 처리 실패"),
    EXIST_ID(200, 1003, "존재하는 ID"),
    VALIDATION_ERROR(400, 1004, "입력값이 유효하지 않음");

    private final Integer httpStatusCode;
    private final Integer errorCode;
    private final String description;
}
