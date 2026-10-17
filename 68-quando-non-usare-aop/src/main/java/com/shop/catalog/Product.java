package com.shop.catalog;

public record Product(long id, String name, int priceInCents) {
    public Product {
        if (priceInCents <= 0) {
            throw new InvalidPriceException(priceInCents);
        }
    }
}
