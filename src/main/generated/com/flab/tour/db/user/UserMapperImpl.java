package com.flab.tour.db.user;

import com.flab.tour.domain.user.controller.model.UserRegisterRequest;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-05-16T22:14:33+0900",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.10 (Oracle Corporation)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public UserEntity toUserEntity(UserRegisterRequest userRegisterRequest) {
        if ( userRegisterRequest == null ) {
            return null;
        }

        UserEntity userEntity = new UserEntity();

        userEntity.setName( userRegisterRequest.getName() );
        userEntity.setEmail( userRegisterRequest.getEmail() );
        userEntity.setPassword( userRegisterRequest.getPassword() );
        userEntity.setPhoneNumber( userRegisterRequest.getPhoneNumber() );
        userEntity.setEmailMarketingConsent( userRegisterRequest.isEmailMarketingConsent() );
        userEntity.setSmsMarketingConsent( userRegisterRequest.isSmsMarketingConsent() );
        userEntity.setPushMarketingConsent( userRegisterRequest.isPushMarketingConsent() );

        userEntity.setJoinedDate( java.time.LocalDate.now() );
        userEntity.setEmailMarketingConsentLastModifiedAt( java.time.LocalDateTime.now() );
        userEntity.setSmsMarketingConsentLastModifiedAt( java.time.LocalDateTime.now() );
        userEntity.setPushMarketingConsentLastModifiedAt( java.time.LocalDateTime.now() );
        userEntity.setCreatedAt( java.time.LocalDateTime.now() );
        userEntity.setModifiedAt( java.time.LocalDateTime.now() );

        return userEntity;
    }
}
