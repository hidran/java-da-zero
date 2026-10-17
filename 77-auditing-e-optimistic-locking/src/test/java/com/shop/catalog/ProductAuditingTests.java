package com.shop.catalog;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Instant;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ProductAuditingTests {
    @Autowired
    ProductService service;
    @Autowired
    ProductJpaRepository entities;

    @Test
    void createSetsBothTimestamps() {
        Product created = service.create("Webcam", 3900);
        ProductEntity entity = entities.findById(created.id()).orElseThrow();
        assertThat(entity.getCreatedAt()).isNotNull();
        assertThat(entity.getUpdatedAt()).isNotNull();
    }

    @Test
    void updateKeepsCreatedAtAndMovesUpdatedAt() {
        Product created = service.create("Lamp", 2900);
        Instant createdAt = entities.findById(created.id()).orElseThrow().getCreatedAt();
        service.update(created.id(), "Desk lamp", 3100, created.version());
        ProductEntity updated = entities.findById(created.id()).orElseThrow();
        assertThat(createdAt).isNotNull();
        assertThat(updated.getCreatedAt()).isEqualTo(createdAt);
        assertThat(updated.getUpdatedAt()).isAfterOrEqualTo(createdAt);
    }
}
