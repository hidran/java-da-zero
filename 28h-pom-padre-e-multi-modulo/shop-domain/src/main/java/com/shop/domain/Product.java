package com.shop.domain;

public record Product(String name, int priceInCents) {
    public Product {
        if (priceInCents < 0) {
            throw new IllegalArgumentException("Price cannot be negative");
        }
    }
}
