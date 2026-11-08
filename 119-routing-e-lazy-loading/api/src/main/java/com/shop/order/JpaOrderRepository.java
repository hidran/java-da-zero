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
    public Order create(List<OrderLine> lines) {
        OrderEntity order = new OrderEntity(OrderStatus.NEW);
        lines.forEach(order::addLine);
        return jpa.save(order).toOrder();
    }
}
