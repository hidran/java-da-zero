package com.shop.application;
import com.shop.domain.Order;
public class PlaceOrderService {
    private final OrderRepository repository;
    public PlaceOrderService(OrderRepository repository) {
        this.repository = repository;
    }
    public Order place(String id, int totalInCents) {
        Order order = new Order(id, totalInCents);
        repository.save(order);
        return order;
    }
}
