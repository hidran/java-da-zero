package com.shop.web;

import com.shop.order.Order;

public record OrderResponse(long id, String product, int totalInCents) {
    public static OrderResponse from(Order order) {
        return new OrderResponse(order.id(), order.product(), order.totalInCents());
    }
}
