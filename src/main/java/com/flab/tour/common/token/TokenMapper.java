package com.flab.tour.common.token;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TokenMapper {
    @Mappings({
            @Mapping(source = "accessToken.token", target = "accessToken"),
            @Mapping(source = "accessToken.expiredAt", target = "accessTokenExpiredAt"),
            @Mapping(source = "refreshToken.token", target = "refreshToken"),
            @Mapping(source = "refreshToken.expiredAt", target = "refreshTokenExpiredAt")
    })
    TokenResponse toResponse(BaseTokenVO accessToken, BaseTokenVO refreshToken);
}
