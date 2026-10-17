package com.shop.catalog;

public record Product(long id, String name, int priceInCents, long version) {
}
