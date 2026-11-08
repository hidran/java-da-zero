package com.shop.catalog;

import org.springframework.data.jpa.repository.JpaRepository;

interface CategoryJpaRepository extends JpaRepository<CategoryEntity, Long> {
}
