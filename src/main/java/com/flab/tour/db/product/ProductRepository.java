package com.flab.tour.db.product;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<ProductAvailabilityEntity, String> {
}

