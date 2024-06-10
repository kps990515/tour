package com.flab.tour.db.reservation;

import com.flab.tour.db.product.ProductAvailabilityEntity;
import com.flab.tour.domain.reservation.controller.model.ReservationSearchResponse;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<ReservationEntity, String> {

    @Query("SELECT new com.example.ReservationSearchResponse(r.reservationId, r.quantity, r.finalPrice, r.status, " +
            "p.productId, p.name, p.category, p.city, p.imageUrl) " +
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

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT p FROM ProductAvailabilityEntity p WHERE p.productId = :productId")
    ProductAvailabilityEntity lockProduct(@Param("productId") String productId);

    @Modifying
    @Query("UPDATE ProductAvailabilityEntity p " +
            "SET p.quantityAvailable = p.quantityAvailable - :quantity, " +
            "p.modifiedAt = CURRENT_TIMESTAMP " +
            "WHERE p.productId = :productId " +
            "AND P.date = :reservationDate " +
            "AND p.quantityAvailable >= :quantity") //query method로 변경(이미 락이 있어서 atomic 필요없음)
    // atomic쿼리의 경우는 lock이 없을떄 활용하면 좋음
    int pessimisticReservate(@Param("productId") String productId, @Param("reservationDate") LocalDate reservationDate, @Param("quantity") int quantity);

    @Modifying
    @Query("UPDATE ProductAvailabilityEntity p " +
            "SET p.quantityAvailable = p.quantityAvailable - :quantity, " +
            "p.modifiedAt = CURRENT_TIMESTAMP " +
            "WHERE p.productId = :productId " +
            "AND P.date = :reservationDate " +
            "AND p.quantityAvailable >= :quantity " +
            "AND p.version = :version")
    int optimistcReservate(@Param("productId") String productId, @Param("reservationDate") LocalDate reservationDate,
                           @Param("quantity") int quantity, @Param("version") int version);
}

