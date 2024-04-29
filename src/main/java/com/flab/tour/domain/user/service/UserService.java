package com.flab.tour.domain.user.service;

import com.flab.tour.common.error.UserErrorCode;
import com.flab.tour.common.exception.ApiException;
import com.flab.tour.config.objectmapper.BaseService;
import com.flab.tour.config.objectmapper.ObjectConvertUtil;
import com.flab.tour.db.user.UserEntity;
import com.flab.tour.db.user.UserMapper;
import com.flab.tour.db.user.UserRepository;
import com.flab.tour.domain.user.controller.model.User;
import com.flab.tour.domain.user.controller.model.UserLoginRequest;
import com.flab.tour.domain.user.controller.model.UserRegisterRequest;
import com.flab.tour.domain.user.controller.model.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class UserService extends BaseService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserResponse login(UserLoginRequest request) {
        var userEntity = userRepository.findFirstByEmailAndPasswordOrderByUserIdDesc(request.getEmail(), request.getPassword());

        if(userEntity.isPresent()){
            return new UserResponse("로그인완료");
        }else{
            throw new ApiException(UserErrorCode.USER_NOT_FOUND);
        }
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

    public User me(UUID userId) {
        var userEntity = getUserWithThrow(userId);
        return ObjectConvertUtil.getInstance().copyVO(userEntity, User.class);
    }

    public UUID getUserId(UserLoginRequest request) {
        return userRepository.findFirstByEmailAndPasswordOrderByUserIdDesc(request.getEmail(), request.getPassword())
                .orElseThrow(() -> new ApiException(UserErrorCode.USER_NOT_FOUND)).getUserId();
    }

    public UserEntity getUserWithThrow(UUID userId){
        return userRepository.findFirstByUserId(userId)
                .orElseThrow(() -> new ApiException(UserErrorCode.USER_NOT_FOUND));
    }
}
