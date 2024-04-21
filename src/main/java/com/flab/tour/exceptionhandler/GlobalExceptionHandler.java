package com.flab.tour.exceptionhandler;

import com.flab.tour.common.api.Api;
import com.flab.tour.common.error.CommonErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
@Order
public class GlobalExceptionHandler {
    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<Api<Object>> exception(Exception exception){

        log.error("", exception);

        return ResponseEntity
                .status(500)
                .body(
                        Api.ERROR(CommonErrorCode.SERVER_ERROR)
                );
    }
}
