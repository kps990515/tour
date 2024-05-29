package com.flab.tour.domain.user.controller;

import com.flab.tour.common.api.Api;
import com.flab.tour.common.token.TokenResponse;
import com.flab.tour.common.token.TokenService;
import com.flab.tour.domain.user.controller.model.*;
import com.flab.tour.domain.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/users/")
public class UserController {
    private final UserService userService;
    private final TokenService tokenService;

    @GetMapping("/me")
    public Api<User> me(User user){
        var response = userService.me(user.getUserId());
        return Api.OK(response);
    }

    @PutMapping("/me")
    public Api<UserResponse> me(User user, @RequestBody UserUpdateRequest request){
        var response = userService.updateProfile(user.getUserId(), request);
        return Api.OK(response);
    }

    @PostMapping("/login")
    public Api<UserResponse> login(@Valid @RequestBody UserLoginRequest request){
        var response = userService.login(request);
        return Api.OK(response);
    }

    @PostMapping("/signup")
    public Api<UserResponse> signup(@Valid @RequestBody UserRegisterRequest request){
        var response = userService.signupByEmail(request);
        return Api.OK(response);
    }

    @PostMapping("/token")
    public Api<TokenResponse> getToken(UserLoginRequest request){
        var response = tokenService.issueToken(request);
        return Api.OK(response);
    }
}
