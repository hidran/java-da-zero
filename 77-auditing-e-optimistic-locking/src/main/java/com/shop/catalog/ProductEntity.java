package com.shop.catalog;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import java.time.Instant;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "product")
@EntityListeners(AuditingEntityListener.class)
public class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 100)
    private String name;
    private int priceInCents;
    @Version
    private long version;
    @CreatedDate
    @Column(updatable = false)
    private Instant createdAt;
    @LastModifiedDate
    private Instant updatedAt;

    protected ProductEntity() {
    }

    private ProductEntity(Long id, String name, int priceInCents, long version) {
        this.id = id;
        this.name = name;
        this.priceInCents = priceInCents;
        this.version = version;
    }

    static ProductEntity from(Product product) {
        Long id = product.id() == 0 ? null : product.id();
        return new ProductEntity(id, product.name(), product.priceInCents(), product.version());
    }

    Product toProduct() {
        return new Product(id, name, priceInCents, version);
    }

    Instant getCreatedAt() {
        return createdAt;
    }

    Instant getUpdatedAt() {
        return updatedAt;
    }
}
