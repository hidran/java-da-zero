package com.shop.order;

public record OrderLine(long productId, String productName, int unitPriceInCents, int quantity) {
    public int subtotalInCents() {
        return unitPriceInCents * quantity;
    }
}
