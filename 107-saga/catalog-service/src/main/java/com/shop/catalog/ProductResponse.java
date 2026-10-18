package com.shop.catalog;

public record ProductResponse(long id, String name, int priceInCents, int stock) {
    public static ProductResponse from(Product product, int stock) {
        return new ProductResponse(product.id(), product.name(), product.priceInCents(), stock);
    }
}
