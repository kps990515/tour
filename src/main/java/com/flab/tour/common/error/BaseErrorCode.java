package com.flab.tour.common.error;

// enum은 상속받을 수 없어 interface생성해서 상속
public interface BaseErrorCode {
    Integer getHttpStatusCode();
    Integer getErrorCode();
    String getDescription();
}
