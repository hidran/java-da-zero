package com.shop.orders;

public record Order(long id, long productId, int quantity, int totalInCents) {
}
