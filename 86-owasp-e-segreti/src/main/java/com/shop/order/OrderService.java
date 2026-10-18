package com.shop.order;

import org.springframework.stereotype.Service;

@Service
public class OrderService {
    private final OrderRepository repository;

    public OrderService(OrderRepository repository) {
        this.repository = repository;
    }

    public Order findForCustomer(long id, String username) {
        return repository.findById(id)
                .filter(order -> order.owner().equals(username))
                .orElseThrow(() -> new OrderNotFoundException(id));
    }
}
