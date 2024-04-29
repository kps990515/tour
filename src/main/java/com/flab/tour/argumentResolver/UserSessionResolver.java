package com.flab.tour.argumentResolver;

import com.flab.tour.common.annotation.UserSession;
import com.flab.tour.common.error.CommonErrorCode;
import com.flab.tour.common.exception.ApiException;
import com.flab.tour.config.objectmapper.ObjectConvertUtil;
import com.flab.tour.domain.user.controller.model.User;
import com.flab.tour.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class UserSessionResolver implements HandlerMethodArgumentResolver {

    private final UserService userService;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        var annotations = parameter.hasParameterAnnotation(UserSession.class);
        var parameterType = parameter.getParameterType().equals(User.class);

        return (annotations && parameterType);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {

        var requestContext = RequestContextHolder.getRequestAttributes();
        var userId = requestContext != null ? requestContext.getAttribute("userId", RequestAttributes.SCOPE_REQUEST) : null;
        if(userId == null){
            throw new ApiException(CommonErrorCode.NULL_POINT, "userId == null");
        }

        var userEntity = userService.getUserWithThrow(UUID.fromString(userId.toString()));

        // 사용자 정보세팅
        return ObjectConvertUtil.getInstance().copyVO(userEntity, User.class);
    }
}
