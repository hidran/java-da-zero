package com.shop.orders;

public record PlaceOrderRequest(long productId, int quantity) {
}
