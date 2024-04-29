package com.flab.tour.db.user;

import com.flab.tour.domain.user.controller.model.UserRegisterRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(target = "joinedDate", expression = "java(java.time.LocalDate.now())")
    @Mapping(target = "emailMarketingConsentLastModifiedAt", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "smsMarketingConsentLastModifiedAt", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "pushMarketingConsentLastModifiedAt", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "modifiedAt", expression = "java(java.time.LocalDateTime.now())")
    UserEntity toUserEntity(UserRegisterRequest userRegisterRequest);

}
