package com.flab.tour.common.api;

import com.flab.tour.common.error.CommonErrorCode;
import com.flab.tour.common.error.BaseErrorCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Result {
    private Integer resultCode;
    private String resultMessage;
    private String resultDescription;

    public static Result OK(){
        return Result.builder()
                .resultCode(HttpStatus.OK.value())
                .resultMessage(CommonErrorCode.OK.getDescription())
                .resultDescription("성공")
                .build();
    }

    public static Result ERROR(BaseErrorCode baseErrorCode){
        return Result.builder()
                .resultCode(baseErrorCode.getErrorCode())
                .resultMessage(baseErrorCode.getDescription())
                .resultDescription("에러")
                .build();
    }

    public static Result ERROR(BaseErrorCode baseErrorCode, Throwable tx){
        return Result.builder()
                .resultCode(baseErrorCode.getErrorCode())
                .resultMessage(baseErrorCode.getDescription())
                .resultDescription(tx.getLocalizedMessage())
                .build();
    }

    public static Result ERROR(BaseErrorCode baseErrorCode, String description){
        return Result.builder()
                .resultCode(baseErrorCode.getErrorCode())
                .resultMessage(baseErrorCode.getDescription())
                .resultDescription(description)
                .build();
    }
}
