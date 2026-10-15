package com.shop.domain;
public record Order(String id, int totalInCents) {
    public Order {
        if (totalInCents <= 0) {
            throw new IllegalArgumentException("total must be positive");
        }
    }
}
