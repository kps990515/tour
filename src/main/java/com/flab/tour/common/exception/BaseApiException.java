package com.flab.tour.common.exception;

import com.flab.tour.common.error.BaseErrorCode;

public interface BaseApiException {
    BaseErrorCode getBaseErrorCode();
    String getErrorDescription();
}
