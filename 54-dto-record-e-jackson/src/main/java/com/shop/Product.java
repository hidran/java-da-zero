package com.shop;

public record Product(long id, String name, int priceInCents, int supplierCostInCents) {
}
