package com.shop.order;

public record Confirmation(long orderId, String thread, boolean virtual) {
}
