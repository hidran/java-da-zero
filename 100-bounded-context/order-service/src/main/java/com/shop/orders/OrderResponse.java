package com.shop.orders;

public record OrderResponse(long id, long productId, String productName, int unitPriceInCents, int quantity,
        int totalInCents) {
    public static OrderResponse from(Order order) {
        return new OrderResponse(order.id(), order.productId(), order.productName(), order.unitPriceInCents(),
                order.quantity(), order.totalInCents());
    }
}
