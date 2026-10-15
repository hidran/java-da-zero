package com.shop.infrastructure;
import com.shop.application.OrderRepository;
import com.shop.domain.Order;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
public class InMemoryOrderRepository implements OrderRepository {
    private final Map<String, Order> orders = new HashMap<>();
    @Override
    public void save(Order order) {
        orders.put(order.id(), order);
    }
    @Override
    public List<Order> findAll() {
        return List.copyOf(orders.values());
    }
}
