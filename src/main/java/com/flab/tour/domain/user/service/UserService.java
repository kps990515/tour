package com.flab.tour.domain.user.service;

import com.flab.tour.common.error.UserErrorCode;
import com.flab.tour.common.exception.ApiException;
import com.flab.tour.config.objectmapper.BaseService;
import com.flab.tour.db.user.UserEntity;
import com.flab.tour.db.user.UserMapper;
import com.flab.tour.db.user.UserRepository;
import com.flab.tour.domain.user.controller.model.UserLoginRequest;
import com.flab.tour.domain.user.controller.model.UserRegisterRequest;
import com.flab.tour.domain.user.controller.model.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService extends BaseService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserResponse login(UserLoginRequest request) {
        var userEntity = userRepository.findFirstByEmailAndPasswordOrderByUserIdDesc(request.getEmail(), request.getPassword())
                .orElseThrow(() -> new ApiException(UserErrorCode.USER_NOT_FOUND));

        return new UserResponse("로그인완료");
    }

    public UserResponse register(UserRegisterRequest svo) {
        userRepository.findFirstByEmail(svo.getEmail())
                .ifPresent(user -> {
                    throw new ApiException(UserErrorCode.EXIST_ID);
                });

        UserEntity newUser = userMapper.toUserEntity(svo);
        userRepository.save(newUser);

        return new UserResponse("가입완료");
    }
}
