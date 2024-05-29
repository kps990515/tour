package com.flab.tour.domain.reservation.controller.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReservationSearchResponse {
    private String reservationId;

    private String productId;

    private String productName;

    private String category;

    private String city;

    private String imageUrl;

    private LocalDate reservationDate;

    private int finalPrice;

    private int quantity;

    private String status;
}
