package com.shop.catalog;
public class Product {
    private final String name;
    final int priceInCents;
    public Product(String name, int priceInCents) {
        this.name = name;
        this.priceInCents = priceInCents;
    }
    public String name() {
        return name;
    }
    public int priceInCents() {
        return priceInCents;
    }
}
