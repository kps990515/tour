package com.flab.tour.db.user;

import com.flab.tour.domain.user.controller.model.UserUpdateRequest;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-05-19T19:47:05+0900",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.10 (Oracle Corporation)"
)
@Component
public class UserUpdateMapperImpl implements UserUpdateMapper {

    @Override
    public void updateUserFromRequest(UserUpdateRequest request, UserEntity entity) {
        if ( request == null ) {
            return;
        }

        if ( request.getName() != null ) {
            entity.setName( request.getName() );
        }
        if ( request.getPassword() != null ) {
            entity.setPassword( request.getPassword() );
        }
        entity.setEmailMarketingConsent( request.isEmailMarketingConsent() );
        entity.setSmsMarketingConsent( request.isSmsMarketingConsent() );
        entity.setPushMarketingConsent( request.isPushMarketingConsent() );
    }
}
