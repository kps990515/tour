package com.flab.tour.domain.user.controller;

import com.flab.tour.common.annotation.UserSession;
import com.flab.tour.common.api.Api;
import com.flab.tour.domain.user.controller.model.User;
import com.flab.tour.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
