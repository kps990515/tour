package com.flab.tour.db.reservation;

import com.flab.tour.domain.reservation.controller.model.ReservationRequest;
import com.flab.tour.domain.user.controller.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ReservationMapper {
    ReservationMapper INSTANCE = Mappers.getMapper(ReservationMapper.class);

    @Mapping(target = "userId", source = "user.userId")
    @Mapping(target = "reservationDate", source = "request.reservationDate", dateFormat = "yyyy-MM-dd")
    @Mapping(target = "status", constant = "NEW")
    ReservationEntity toNewReservation(User user, ReservationRequest request);

}
