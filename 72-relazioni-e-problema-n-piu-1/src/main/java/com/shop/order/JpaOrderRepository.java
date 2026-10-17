package com.shop.order;

import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class JpaOrderRepository implements OrderRepository {
    private final OrderJpaRepository jpa;

    public JpaOrderRepository(OrderJpaRepository jpa) {
        this.jpa = jpa;
    }

    @Override
    public List<Order> findAll() {
        return jpa.findAllWithLines().stream().map(OrderEntity::toOrder).toList();
    }
}
