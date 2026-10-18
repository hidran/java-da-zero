package com.shop.orders;

public record OrderCancelled(long orderId, long productId, int quantity) {
}
