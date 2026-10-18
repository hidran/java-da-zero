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

    @Column(nullable = false)
    private String name;

    @Column(name = "price_in_cents", nullable = false)
    private int priceInCents;

    protected ProductEntity() {
    }

    ProductEntity(Long id, String name, int priceInCents) {
        this.id = id;
        this.name = name;
        this.priceInCents = priceInCents;
    }

    static ProductEntity from(Product product) {
        return new ProductEntity(product.id() > 0 ? product.id() : null, product.name(), product.priceInCents());
    }

    Product toProduct() {
        return new Product(id, name, priceInCents);
    }
}
