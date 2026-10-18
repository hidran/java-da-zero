package com.shop.orders;

public record OrderPlaced(long orderId, long productId, int quantity, int totalInCents) {
}
