package com.shop.order;

import org.springframework.data.jpa.repository.JpaRepository;

interface OrderLineJpaRepository extends JpaRepository<OrderLineEntity, Long> {
}
