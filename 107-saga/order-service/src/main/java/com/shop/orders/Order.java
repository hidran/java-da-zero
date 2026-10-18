package com.shop.orders;

public record Order(long id, long productId, int quantity, int totalInCents, OrderStatus status) {
    public Order withStatus(OrderStatus newStatus) {
        return new Order(id, productId, quantity, totalInCents, newStatus);
    }
}
