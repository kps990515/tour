package com.flab.tour.common.token;

import java.util.Map;

public interface BaseToken {
    BaseTokenVO issueAccessToken(Map<String, Object> data);
    BaseTokenVO issueRefreshToken(Map<String, Object> data);
    Map<String, Object> validationTokenWithThrow(String token);
}
