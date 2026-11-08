package com.shop.web;

import com.shop.catalog.Product;

public record ProductResponse(
        long id, String name, String description, int priceInCents, long categoryId, String categoryName) {
    public static ProductResponse from(Product product) {
        return new ProductResponse(product.id(), product.name(), product.description(),
                product.priceInCents(), product.category().id(), product.category().name());
    }
}
