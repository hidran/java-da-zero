package com.shop.domain;
public record Product(String sku, String name, int priceInCents) {
    public Product {
        if (priceInCents <= 0) {
            throw new IllegalArgumentException("Price must be positive");
        }
    }
}
