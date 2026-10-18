package com.shop.orders;

public interface OrderEventPublisher {
    void publish(OrderPlaced event);
}
