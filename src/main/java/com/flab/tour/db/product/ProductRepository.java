package com.flab.tour.db.product;

import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;

public interface ProductRepository extends JpaRepository<ProductAvailabilityEntity, String> {

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

