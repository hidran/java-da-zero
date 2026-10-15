package com.shop.catalog;
public class CatalogService {
    private final InMemoryProductRepository repository;
    public CatalogService(InMemoryProductRepository repository) {
        this.repository = repository;
        System.out.println("CatalogService created");
    }
    public int totalInCents(String name, int quantity) {
        return repository.priceOf(name) * quantity;
    }
}
