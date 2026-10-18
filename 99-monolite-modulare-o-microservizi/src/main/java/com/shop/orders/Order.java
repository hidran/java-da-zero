package com.shop.orders;

public record Order(long productId, String productName, int quantity, int totalInCents) {
}
