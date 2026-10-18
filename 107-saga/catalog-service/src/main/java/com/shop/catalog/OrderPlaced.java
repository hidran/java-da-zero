package com.shop.catalog;

public record OrderPlaced(long orderId, long productId, int quantity, int totalInCents) {
}
