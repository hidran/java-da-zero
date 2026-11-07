package com.shop.domain;
public record OrderLine(Product product, int quantity) {
    public OrderLine {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive");
        }
    }
    public int subtotalInCents() {
        return product.priceInCents() * quantity;
    }
}
