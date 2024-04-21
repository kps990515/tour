package com.flab.tour.common.exception;

import com.flab.tour.common.error.BaseErrorCode;
import lombok.Getter;

@Getter
public class ApiException extends RuntimeException implements BaseApiException {

    private final BaseErrorCode baseErrorCode;
    private final String errorDescription;

    public ApiException(BaseErrorCode baseErrorCode){
        super(baseErrorCode.getDescription());
        this.baseErrorCode = baseErrorCode;
        this.errorDescription = baseErrorCode.getDescription();
    }

    public ApiException(BaseErrorCode baseErrorCode, String errorDescription){
        super(errorDescription);
        this.baseErrorCode = baseErrorCode;
        this.errorDescription = errorDescription;
    }

    public ApiException(BaseErrorCode baseErrorCode, Throwable tx){
        super(tx);
        this.baseErrorCode = baseErrorCode;
        this.errorDescription = baseErrorCode.getDescription();
    }

    public ApiException(BaseErrorCode baseErrorCode, Throwable tx, String errorDescription){
        super(tx);
        this.baseErrorCode = baseErrorCode;
        this.errorDescription = errorDescription;
    }
}
