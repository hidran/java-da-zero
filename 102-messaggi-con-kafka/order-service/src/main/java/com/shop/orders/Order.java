package com.shop.orders;

public record Order(long id, long productId, String productName, int unitPriceInCents,
        int quantity) {
    public int totalInCents() {
        return unitPriceInCents * quantity;
    }
}
