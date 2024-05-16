package com.flab.tour.exceptionhandler;

import com.flab.tour.common.api.Api;
import com.flab.tour.common.error.UserErrorCode;
import com.flab.tour.common.exception.ApiException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
@Order(Integer.MIN_VALUE)
public class ApiExceptionHandler {
    @ExceptionHandler(ApiException.class)
    public ResponseEntity<Api<Object>> apiExceiption(ApiException apiExceiption) {
        log.error("", apiExceiption);

        var errorCode = apiExceiption.getBaseErrorCode();

        return ResponseEntity
                .status(errorCode.getHttpStatusCode())
                .body(
                        Api.ERROR(errorCode, apiExceiption.getErrorDescription())
                );
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Api<Object>> handleConstraintViolation(ConstraintViolationException e) {
        Map<String, String> errors = e.getConstraintViolations().stream()
                .collect(Collectors.toMap(
                        violation -> violation.getPropertyPath().toString(),
                        ConstraintViolation::getMessage,
                        (existing, replacement) -> existing)); // 중복 키 처리

        ApiException apiException = new ApiException(UserErrorCode.VALIDATION_ERROR, errors.toString());
        return ResponseEntity
                .status(apiException.getBaseErrorCode().getHttpStatusCode())
                .body(Api.ERROR(apiException.getBaseErrorCode(), apiException.getErrorDescription()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Api<Object>> handleMethodArgumentNotValideException(MethodArgumentNotValidException e) {
        Map<String, String> errors = e.getBindingResult().getAllErrors().stream()
                .collect(Collectors.toMap(
                        error -> ((FieldError) error).getField(),
                        error -> error.getDefaultMessage() != null ? error.getDefaultMessage() : "Unknown error",
                        (existing, replacement) -> existing));

        ApiException apiException = new ApiException(UserErrorCode.VALIDATION_ERROR, errors.toString());
        return ResponseEntity
                .status(apiException.getBaseErrorCode().getHttpStatusCode())
                .body(Api.ERROR(apiException.getBaseErrorCode(), apiException.getErrorDescription()));
    }
}
