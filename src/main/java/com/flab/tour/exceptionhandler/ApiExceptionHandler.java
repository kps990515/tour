package com.flab.tour.exceptionhandler;

import com.flab.tour.common.api.Api;
import com.flab.tour.common.exception.ApiException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
@Order(Integer.MIN_VALUE)
public class ApiExceptionHandler {
    @ExceptionHandler(ApiException.class)
    public ResponseEntity<Api<Object>> apiExceiption(ApiException apiExceiption){
        log.error("", apiExceiption);

        var errorCode = apiExceiption.getBaseErrorCode();

        return ResponseEntity
                .status(errorCode.getHttpStatusCode())
                .body(
                        Api.ERROR(errorCode, apiExceiption.getErrorDescription())
                );
    }
}
