package com.shop.orders;

public record OrderPlaced(long orderId, long productId, int quantity, int totalInCents) {
    public static OrderPlaced from(Order order) {
        return new OrderPlaced(order.id(), order.productId(), order.quantity(),
                order.totalInCents());
    }
}
