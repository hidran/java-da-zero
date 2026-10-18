package com.shop.catalog;

public record ProductResponse(long id, String name, int priceInCents) {
    public static ProductResponse from(Product product) {
        return new ProductResponse(product.id(), product.name(), product.priceInCents());
    }
}
