package com.flab.tour.domain.user.service;

import com.flab.tour.config.objectmapper.BaseService;
import com.flab.tour.db.user.UserEntity;
import com.flab.tour.domain.user.controller.model.UserLoginRequest;
import com.flab.tour.domain.user.controller.model.UserLoginResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService extends BaseService {

    public UserLoginResponse login(UserLoginRequest svo) {
        var userEntity = copyVO(svo, UserEntity.class);
        var userResponse = copyVO(svo, UserLoginResponse.class);

        // TODO 토큰생성
        return (UserLoginResponse) userResponse;
    }
}
