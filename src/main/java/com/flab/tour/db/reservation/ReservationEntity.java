package com.flab.tour.db.reservation;

import com.flab.tour.db.BaseEntity;
import com.flab.tour.db.product.ProductAvailabilityEntity;
import com.flab.tour.db.user.UserEntity;
import com.github.f4b6a3.uuid.UuidCreator;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Persistable;

import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "reservation")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReservationEntity extends BaseEntity implements Persistable<String>{
    @Id
    @Column(name = "reservation_id", nullable = false)
    private String reservationId;

    @PrePersist
    private void generateUUID(){
        this.reservationId = UuidCreator.getTimeOrderedEpoch().toString();
    }

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private ProductAvailabilityEntity product;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @Column(name = "product_id", nullable = false)
    private String productId;

    @Column(name = "user_id", nullable = false)
    private String userId;

    @Column(name = "reservation_date", nullable = false)
    private LocalDate reservationDate;

    @Column(name = "price", nullable = false)
    private int price;

    @Column(name = "coupon_use", nullable = false)
    private boolean couponUse;

    @Column(name = "coupon_discount_price", nullable = false)
    private int couponDiscountPrice;

    @Column(name = "point_use", nullable = false)
    private boolean pointUse;

    @Column(name = "point_discount_price", nullable = false)
    private int pointDiscountPrice;

    @Column(name = "final_price", nullable = false)
    private int finalPrice;

    @Column(name = "quantity", nullable = false)
    private int quantity;

    @Column(name = "extra_request")
    private String extraRequest;

    @Column(name = "pay_method", nullable = false)
    private String payMethod;

    @Column(name = "status", nullable = false)
    private String status;

    @Override
    @Transient
    public boolean isNew() {
        return getCreatedAt() == null || getCreatedAt().equals(getModifiedAt());
    }

    @Override
    public String getId() {
        return this.reservationId;
    }

}
