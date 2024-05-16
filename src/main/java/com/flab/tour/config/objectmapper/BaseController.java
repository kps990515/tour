package com.flab.tour.config.objectmapper;

public class BaseController {
    public <T> T copyVO(Object fromValue, Class<T> toValueType){
        return ObjectConvertUtil.getInstance().copyVO(fromValue, toValueType);
    }
}
