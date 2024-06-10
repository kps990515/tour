package com.flab.tour.db.reservation;

import com.flab.tour.domain.reservation.controller.model.ReservationSearchResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<ReservationEntity, String> {

    @Query("SELECT r.reservationId, r.quantity, r.finalPrice, r.status, " +
            "p.productId, p.name, p.category, p.city, p.imageUrl " +
            "FROM ReservationEntity r " +
            "JOIN FETCH r.product p " +
            "JOIN FETCH r.user u " +
            "WHERE u.userId = :userId " +
            "AND r.reservationDate BETWEEN :startDate AND :endDate " +
            "AND r.status = :status")
    // native쿼리는 컴파일시점에 에러체크불가능
    // fetch join : 성능 최적화와 N+1 문제를 해결
    List<ReservationSearchResponse> findAllReservations(@Param("userId") String userId,
                                                        @Param("startDate") LocalDate startDate,
                                                        @Param("endDate") LocalDate endDate,
                                                        @Param("status") String status);
}

