package com.shop.order;

import java.util.Optional;

public interface OrderRepository {
    Optional<Order> findById(long id);
}
