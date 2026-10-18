package com.shop.orders;

public record OrderResponse(long id, long productId, int quantity, int totalInCents, OrderStatus status) {
    public static OrderResponse from(Order order) {
        return new OrderResponse(order.id(), order.productId(), order.quantity(), order.totalInCents(),
                order.status());
    }
}
