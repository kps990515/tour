package com.flab.tour.controller;

import com.flab.tour.common.api.Api;
import com.flab.tour.common.error.CommonErrorCode;
import com.flab.tour.common.exception.ApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class TestController {
    @GetMapping("/test")
    public Api<TestVo> save(){
        var response = TestVo.builder()
                .name("a")
                .email("a@gmail.com")
                .registerdAt(LocalDateTime.now())
                .build();

        var str = "안녕";
        try{
            var age = Integer.parseInt(str);
        }catch (Exception e){
            throw new ApiException(CommonErrorCode.SERVER_ERROR, e, "사용자 호출 에러 발생");
        }

        return Api.OK(response);
    }
}
