package com.flab.tour.common.token;

import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-05-19T19:47:05+0900",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.10 (Oracle Corporation)"
)
@Component
public class TokenMapperImpl implements TokenMapper {

    @Override
    public TokenResponse toResponse(BaseTokenVO accessToken, BaseTokenVO refreshToken) {
        if ( accessToken == null && refreshToken == null ) {
            return null;
        }

        TokenResponse.TokenResponseBuilder tokenResponse = TokenResponse.builder();

        if ( accessToken != null ) {
            tokenResponse.accessToken( accessToken.getToken() );
            tokenResponse.accessTokenExpiredAt( accessToken.getExpiredAt() );
        }
        if ( refreshToken != null ) {
            tokenResponse.refreshToken( refreshToken.getToken() );
            tokenResponse.refreshTokenExpiredAt( refreshToken.getExpiredAt() );
        }

        return tokenResponse.build();
    }
}
