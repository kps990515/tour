package com.flab.tour.db.product;

import com.flab.tour.db.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Persistable;

import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "product_availability")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductAvailabilityEntity extends BaseEntity implements Persistable<String>{

    @Id
    @Column(name = "product_id", nullable = false)
    private String productId;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "quantity_available", nullable = false)
    private int quantityAvailable;

    @Version
    @Column(name = "version", nullable = false)
    private int version;

    @Transient
    private boolean isNew = true;

    @PostLoad
    @PostPersist
    @PostUpdate
    private void markNotNew() {
        this.isNew = false;
    }

    @Override
    public String getId() {
        return this.productId;
    }

    @Override
    public boolean isNew() {
        return isNew;
    }
}
