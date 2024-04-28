package com.flab.tour.domain.user.controller;

import com.flab.tour.common.api.Api;
import com.flab.tour.config.objectmapper.BaseController;
import com.flab.tour.domain.user.controller.model.UserLoginRequest;
import com.flab.tour.domain.user.controller.model.UserLoginResponse;
import com.flab.tour.domain.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class TestController extends BaseController {
    private final UserService userService;

    @PostMapping("/login")
    public Api<UserLoginResponse> login(@Valid @RequestBody Api<UserLoginRequest> request){
        UserLoginRequest svo = copyVO(request, UserLoginRequest.class);
        var response = userService.login(svo);
        return Api.OK(response);
    }
}
