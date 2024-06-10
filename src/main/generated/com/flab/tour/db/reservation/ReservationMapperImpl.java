package com.flab.tour.db.reservation;

import javax.annotation.processing.Generated;

import com.flab.tour.domain.reservation.controller.model.ReservationRequest;
import com.flab.tour.domain.user.controller.model.User;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-06-03T20:16:09+0900",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.10 (Oracle Corporation)"
)
@Component
public class ReservationMapperImpl implements ReservationMapper {
    @Override
    public ReservationEntity toNewReservation(User user, ReservationRequest request) {
        return null;
    }
}
