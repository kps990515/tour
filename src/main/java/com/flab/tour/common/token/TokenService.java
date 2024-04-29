package com.flab.tour.common.token;

import com.flab.tour.common.error.CommonErrorCode;
import com.flab.tour.common.exception.ApiException;
import com.flab.tour.domain.user.controller.model.UserLoginRequest;
import com.flab.tour.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Objects;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class TokenService {
    private final BaseToken baseToken;
    private final TokenMapper tokenMapper;
    private final UserService userService;

    public TokenResponse issueToken(UserLoginRequest request){
        UUID userId = userService.getUserId(request);
        var accessToken = issuesAccessToken(userId);
        var refreshToekn = issuesRefreshToken(userId);
        return toResponse(accessToken, refreshToekn);
    }

    public BaseTokenVO issuesAccessToken(UUID userId){
        var data = new HashMap<String, Object>();
        data.put("userId", userId);
        return baseToken.issueAccessToken(data);
    }

    public BaseTokenVO issuesRefreshToken(UUID userId){
        var data = new HashMap<String, Object>();
        data.put("userId", userId);
        return baseToken.issueRefreshToken(data);
    }

    public UUID validationToken(String token){
        var map = baseToken.validationTokenWithThrow(token);
        var userId = map.get("userId");

        Objects.requireNonNull(userId, () -> {throw new ApiException(CommonErrorCode.NULL_POINT, "token에 할당된 사용자 없음");});

        return UUID.fromString(userId.toString());
    }

    public TokenResponse toResponse(BaseTokenVO accessToken, BaseTokenVO refreshToken){
        Objects.requireNonNull(accessToken, () -> {throw new ApiException(CommonErrorCode.NULL_POINT, "no AccessToken");});
        Objects.requireNonNull(refreshToken, () -> {throw new ApiException(CommonErrorCode.NULL_POINT, "no RefreshToken");});

        return tokenMapper.toResponse(accessToken, refreshToken);

    }
}
