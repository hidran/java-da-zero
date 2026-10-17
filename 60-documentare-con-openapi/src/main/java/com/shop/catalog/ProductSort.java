package com.shop.catalog;

import java.util.Comparator;

public enum ProductSort {
    ID(Comparator.comparingLong(Product::id)),
    NAME(Comparator.comparing(Product::name)),
    PRICE(Comparator.comparingInt(Product::priceInCents));

    private final Comparator<Product> comparator;

    ProductSort(Comparator<Product> comparator) {
        this.comparator = comparator;
    }

    public Comparator<Product> comparator() {
        return comparator;
    }
}
