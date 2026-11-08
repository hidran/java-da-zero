package com.shop.catalog;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

interface ProductJpaRepository extends JpaRepository<ProductEntity, Long> {
    @Query("select p from ProductEntity p join fetch p.category order by p.id")
    List<ProductEntity> findAllWithCategory();

    @Query("select p from ProductEntity p join fetch p.category where p.id = :id")
    Optional<ProductEntity> findByIdWithCategory(long id);

    boolean existsByNameIgnoreCase(String name);
}
