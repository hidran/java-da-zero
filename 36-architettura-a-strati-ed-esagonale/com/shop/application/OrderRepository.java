package com.shop.application;
import com.shop.domain.Order;
import java.util.List;
public interface OrderRepository {
    void save(Order order);
    List<Order> findAll();
}
