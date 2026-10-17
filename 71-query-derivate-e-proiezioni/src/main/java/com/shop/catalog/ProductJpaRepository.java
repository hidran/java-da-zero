package com.shop.catalog;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

interface ProductJpaRepository extends JpaRepository<ProductEntity, Long> {
    List<ProductEntity> findByNameContainingIgnoreCaseOrderByName(String text);
    @Query("select p from ProductEntity p where p.priceInCents between :min and :max"
            + " order by p.priceInCents")
    List<ProductEntity> findInPriceRange(int min, int max);
    List<ProductSummary> findByPriceInCentsLessThanEqualOrderByPriceInCents(int max);
}
