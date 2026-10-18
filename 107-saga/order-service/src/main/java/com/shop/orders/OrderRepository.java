package com.shop.orders;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.stereotype.Repository;

@Repository
public class OrderRepository {
    private final Map<Long, Order> orders = new ConcurrentHashMap<>();
    private final AtomicLong nextId = new AtomicLong(1);

    public Order create(long productId, int quantity, int totalInCents) {
        Order order = new Order(nextId.getAndIncrement(), productId, quantity, totalInCents, OrderStatus.PENDING);
        orders.put(order.id(), order);
        return order;
    }

    public void save(Order order) {
        orders.put(order.id(), order);
    }

    public Optional<Order> findById(long id) {
        return Optional.ofNullable(orders.get(id));
    }
}
