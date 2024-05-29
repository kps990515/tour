package com.flab.tour.db.reservation;

import com.flab.tour.domain.reservation.controller.model.ReservationSearchResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface ReservationRepository extends JpaRepository<ReservationEntity, String> {
    @Query(value = "SELECT A.reservation_id AS reservationId, A.quantity, A.final_price AS finalPrice, A.status, " +
                          "B.product_id AS productId, B.name AS productName, B.category, B.city, B.image_url AS imageUrl " +
                   "FROM reservation A " +
                   "LEFT JOIN products B ON A.product_id = B.product_id " +
                   "WHERE A.user_id = :userId " +
                   "AND A.reservation_date BETWEEN :startDate AND :endDate " +
                   "AND A.status = :status", nativeQuery = true)
    List<ReservationSearchResponse> findAllReservations(@Param("userId") String userId,
                                                        @Param("startDate") LocalDate startDate,
                                                        @Param("endDate") LocalDate endDate,
                                                        @Param("status") String status);
}
