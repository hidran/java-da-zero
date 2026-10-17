package com.shop.web;

import com.shop.catalog.Product;

public record ProductResponseV2(long id, String name, PriceResponse price) {
    public static ProductResponseV2 from(Product product) {
        PriceResponse price = new PriceResponse(product.priceInCents(), "EUR");
        return new ProductResponseV2(product.id(), product.name(), price);
    }
}
