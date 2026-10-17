package com.shop.order;

import java.util.List;

public interface OrderRepository {
    List<Order> findAll();
}
