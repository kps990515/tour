package com.flab.tour.common.api;

import com.flab.tour.common.error.BaseErrorCode;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Api<T> {

    private Result result;

    @Valid
    private T response;

    public static <T> Api<T> OK(T response){
        var api = new Api<T>();
        api.result = Result.OK();
        api.response = response;
        return api;
    }

    public static Api<Object> ERROR(Result result){
        var api = new Api<Object>();
        api.result = result;
        return api;
    }

    public static Api<Object> ERROR(BaseErrorCode baseErrorCode){
        var api = new Api<Object>();
        api.result = Result.ERROR(baseErrorCode);
        return api;
    }

    public static Api<Object> ERROR(BaseErrorCode baseErrorCode, Throwable tx){
        var api = new Api<Object>();
        api.result = Result.ERROR(baseErrorCode, tx);
        return api;
    }

    public static Api<Object> ERROR(BaseErrorCode baseErrorCode, String description){
        var api = new Api<Object>();
        api.result = Result.ERROR(baseErrorCode, description);
        return api;
    }
}
