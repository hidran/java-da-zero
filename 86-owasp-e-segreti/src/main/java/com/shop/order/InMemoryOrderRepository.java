package com.shop.order;

import java.util.Map;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public class InMemoryOrderRepository implements OrderRepository {
    // Demo data: one order for each demo user
    private final Map<Long, Order> orders = Map.of(
            1L, new Order(1, "alice", "Keyboard", 4900),
            2L, new Order(2, "bob", "Monitor", 18900));

    @Override
    public Optional<Order> findById(long id) {
        return Optional.ofNullable(orders.get(id));
    }
}
