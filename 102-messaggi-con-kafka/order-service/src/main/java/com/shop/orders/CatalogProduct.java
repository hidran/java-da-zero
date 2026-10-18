package com.shop.orders;

public record CatalogProduct(long id, String name, int priceInCents) {
    public ProductSnapshot toSnapshot() {
        return new ProductSnapshot(id, name, priceInCents);
    }
}
