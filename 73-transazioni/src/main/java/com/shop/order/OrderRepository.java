package com.shop.order;

import java.util.Optional;

public interface OrderRepository {
    long create();

    void addLine(long orderId, long productId, int quantity);

    Optional<Order> findById(long id);
}
