package com.flab.tour;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.flab.tour.common.exception.ApiException;
import com.flab.tour.db.user.UserEntity;
import com.flab.tour.db.user.UserMapper;
import com.flab.tour.db.user.UserRepository;
import com.flab.tour.db.user.UserUpdateMapper;
import com.flab.tour.domain.user.controller.model.*;
import com.flab.tour.domain.user.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class ReservationServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @Mock
    private UserUpdateMapper userUpdateMapper;

    @InjectMocks
    private UserService userService;

    private UserEntity userEntity;
    private UserLoginRequest userLoginRequest;
    private UserRegisterRequest userRegisterRequest;
    private UserUpdateRequest userUpdateRequest;

    @BeforeEach
    void setUp() {
        userEntity = new UserEntity();
        userEntity.setUserId("test");
        userEntity.setEmail("test@example.com");
        userEntity.setPassword("password");

        User user = new User();
        user.setUserId("test");
        user.setEmail("test@example.com");

        userLoginRequest = new UserLoginRequest();
        userLoginRequest.setEmail("adf@gmail.com");
        userLoginRequest.setPassword("1234");

        userRegisterRequest = new UserRegisterRequest();
        userRegisterRequest.setEmail("test@gmail.com");
        userRegisterRequest.setName("테스트");
        userRegisterRequest.setPassword("1234");
        userRegisterRequest.setPhoneNumber("010-1111-1111");
    }

    @Test
    // 성공 로그인 케이스
    public void succesLoginTest() {
        when(userRepository.findFirstByEmailAndPasswordOrderByUserIdDesc(userLoginRequest.getEmail(), userLoginRequest.getPassword()))
                .thenReturn(Optional.of(userEntity));

        UserResponse response = userService.login(userLoginRequest);

        assertEquals("로그인완료", response.getMessage());
    }

    @Test
    // 실패 로그인 케이스 - 유저없음
    public void userNotFoundLoginTest() {
        when(userRepository.findFirstByEmailAndPasswordOrderByUserIdDesc(userLoginRequest.getEmail(), userLoginRequest.getPassword()))
                .thenReturn(Optional.empty());

        assertThrows(ApiException.class, () -> {
            userService.login(userLoginRequest);
        });
    }

    @Test
    // 이메일 회원 가입 테스트
    public void successSignupTest() {
        when(userRepository.findFirstByEmail(userRegisterRequest.getEmail()))
                .thenReturn(Optional.empty());

        when(userMapper.toUserEntity(userRegisterRequest)).thenReturn(userEntity);

        UserResponse response = userService.signupByEmail(userRegisterRequest);
        assertEquals("가입완료", response.getMessage());
    }

    @Test
    // 실패 가입 케이스 - 이미 존재하는 유저
    public void userExistsSignupTest() {
        when(userRepository.findFirstByEmail(userRegisterRequest.getEmail()))
                .thenReturn(Optional.of(userEntity));

        assertThrows(ApiException.class, () -> {
            userService.signupByEmail(userRegisterRequest);
        });
    }

    @Test
    public void findUserTest() {
        when(userRepository.findFirstByUserId("test")).thenReturn(Optional.of(userEntity));

        User result = userService.me("test");

        assertNotNull(result);
        assertEquals("test", result.getUserId());
    }

    @Test
    public void NoUserfindUserTest() {
        when(userRepository.findFirstByUserId("test")).thenReturn(Optional.empty());

        assertThrows(ApiException.class, () -> {
            userService.me("test");
        });
    }

    @Test
    public void updateProfileTest() {
        when(userRepository.findFirstByUserId("test")).thenReturn(Optional.of(userEntity));

        UserResponse response = userService.updateProfile("test", userUpdateRequest);
        assertEquals("프로필 수정완료", response.getMessage());
    }
}

