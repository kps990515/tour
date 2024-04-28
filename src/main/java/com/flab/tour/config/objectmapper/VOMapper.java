package com.flab.tour.config.objectmapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface VOMapper {
    VOMapper INSTANCE = Mappers.getMapper(VOMapper.class);
}
