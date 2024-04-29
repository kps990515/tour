package com.flab.tour.domain.user.controller;

import com.flab.tour.common.api.Api;
import com.flab.tour.common.token.TokenResponse;
import com.flab.tour.common.token.TokenService;
import com.flab.tour.config.objectmapper.BaseController;
import com.flab.tour.domain.user.controller.model.UserLoginRequest;
import com.flab.tour.domain.user.controller.model.UserRegisterRequest;
import com.flab.tour.domain.user.controller.model.UserResponse;
import com.flab.tour.domain.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/open-api/user/")
public class UserOpenApiController extends BaseController {
    private final UserService userService;
    private final TokenService tokenService;

    @PostMapping("/login")
    public Api<UserResponse> login(@Valid @RequestBody UserLoginRequest request){
        var response = userService.login(request);
        return Api.OK(response);
    }

    @PostMapping("/register")
    public Api<UserResponse> register(@Valid @RequestBody UserRegisterRequest request){
        var response = userService.register(request);
        return Api.OK(response);
    }

    @PostMapping("/get-token")
    public Api<TokenResponse> getToken(UserLoginRequest request){
        var response = tokenService.issueToken(request);
        return Api.OK(response);
    }
}
