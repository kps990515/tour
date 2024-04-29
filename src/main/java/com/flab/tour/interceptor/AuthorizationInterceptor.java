package com.flab.tour.interceptor;

import com.flab.tour.common.error.CommonErrorCode;
import com.flab.tour.common.error.TokenErrorCode;
import com.flab.tour.common.exception.ApiException;
import com.flab.tour.common.token.TokenService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;
import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
@Component
public class AuthorizationInterceptor implements HandlerInterceptor {

    private final TokenService tokenService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("Authorization Interceptor uri : {}", request.getRequestURI());

        // Web의 경우 Option 메소드일때 Pass
        if(HttpMethod.OPTIONS.matches(request.getMethod())){
            return true;
        }

        // js, html, png resource 요청하는 경우 Pass
        if(handler instanceof ResourceHttpRequestHandler){
            return true;
        }

        // Token 검증
        var accessToken = request.getHeader("authorization-token");
        if(StringUtils.isBlank(accessToken)){
            throw new ApiException(TokenErrorCode.TOKEN_NOT_FOUND);
        }

        var userId = tokenService.validationToken(accessToken);

        if(userId != null){
            // RequestContextHolder : 현재 스레드와 관련된 요청 정보를 RequestAttributes 객체에 저장, 요청 파라미터, 세션 데이터, 헤더 정보 등을 조회
            // 범위는 Thread Local : 하나의 쓰레드에서 읽고 쓸 수 있는 지역변수
            var requestContext = Objects.requireNonNull(RequestContextHolder.getRequestAttributes());
            requestContext.setAttribute("userId", userId, RequestAttributes.SCOPE_REQUEST);
            return true;
        }

        throw new ApiException(CommonErrorCode.UNAUTHORIZED);
    }
}
