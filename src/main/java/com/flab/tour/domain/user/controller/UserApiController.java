package com.flab.tour.domain.user.controller;

import com.flab.tour.common.annotation.UserSession;
import com.flab.tour.common.api.Api;
import com.flab.tour.domain.user.controller.model.User;
import com.flab.tour.domain.user.controller.model.UserResponse;
import com.flab.tour.domain.user.controller.model.UserUpdateRequest;
import com.flab.tour.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/user/")
public class UserApiController {
    private final UserService userService;

    @PostMapping("/me")
    public Api<User> me(@UserSession User user){
        var response = userService.me(user.getUserId());
        return Api.OK(response);
    }

    @PatchMapping("/me")
    public Api<UserResponse> me(@UserSession User user, @RequestBody UserUpdateRequest request){
        var response = userService.updateProfile(user.getUserId(), request);
        return Api.OK(response);
    }
}
