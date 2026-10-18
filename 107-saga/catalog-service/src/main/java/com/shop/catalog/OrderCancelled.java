package com.shop.catalog;

public record OrderCancelled(long orderId, long productId, int quantity) {
}
