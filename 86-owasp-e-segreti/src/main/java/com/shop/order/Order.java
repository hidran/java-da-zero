package com.shop.order;

public record Order(long id, String owner, String product, int totalInCents) {
}
