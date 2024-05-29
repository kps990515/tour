package com.flab.tour.db.reservation;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ReservationMapper {
    ReservationMapper INSTANCE = Mappers.getMapper(ReservationMapper.class);

    //@Mapping(target = "reservationDate", expression = "java(java.time.LocalDate.now())")
    //ReservationEntity toReservationEntity(ReservationRegisterRequest reservationRegisterRequest);

}
