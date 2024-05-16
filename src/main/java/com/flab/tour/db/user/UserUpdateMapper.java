package com.flab.tour.db.user;

import com.flab.tour.domain.user.controller.model.UserUpdateRequest;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UserUpdateMapper {
    void updateUserFromRequest(UserUpdateRequest request, @MappingTarget UserEntity entity);
}
