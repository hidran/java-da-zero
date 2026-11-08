package com.shop.catalog;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "product")
public class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 100)
    private String name;
    @Column(nullable = false, length = 500)
    private String description;
    private int priceInCents;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "category_id")
    private CategoryEntity category;

    protected ProductEntity() {
    }

    ProductEntity(Long id, String name, String description, int priceInCents, CategoryEntity category) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.priceInCents = priceInCents;
        this.category = category;
    }

    Product toProduct() {
        return new Product(id, name, description, priceInCents, category.toCategory());
    }
}
