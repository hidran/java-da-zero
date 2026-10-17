package com.shop.catalog;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "product")
public class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 100)
    private String name;
    private int priceInCents;

    protected ProductEntity() {
    }

    private ProductEntity(Long id, String name, int priceInCents) {
        this.id = id;
        this.name = name;
        this.priceInCents = priceInCents;
    }

    static ProductEntity from(Product product) {
        Long id = product.id() == 0 ? null : product.id();
        return new ProductEntity(id, product.name(), product.priceInCents());
    }

    public Product toProduct() {
        return new Product(id, name, priceInCents);
    }
}
