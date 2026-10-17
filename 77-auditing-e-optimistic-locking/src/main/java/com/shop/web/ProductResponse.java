package com.shop.web;

import com.shop.catalog.Product;

public record ProductResponse(long id, String name, int priceInCents, long version) {
    static ProductResponse from(Product product) {
        return new ProductResponse(product.id(), product.name(), product.priceInCents(), product.version());
    }
}
