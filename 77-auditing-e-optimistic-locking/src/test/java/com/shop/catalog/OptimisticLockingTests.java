package com.shop.catalog;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.orm.ObjectOptimisticLockingFailureException;

@SpringBootTest
class OptimisticLockingTests {
    @Autowired
    ProductService service;

    @Test
    void updateWithTheCurrentVersionIsAccepted() {
        Product created = service.create("Headset", 5900);
        Product updated = service.update(created.id(), "Headset", 5400, created.version());
        assertThat(updated.priceInCents()).isEqualTo(5400);
    }

    @Test
    void updateWithAStaleVersionIsRejected() {
        Product created = service.create("Speaker", 7900);
        service.update(created.id(), "Speaker", 7500, created.version());
        assertThatThrownBy(() -> service.update(created.id(), "Speaker", 6900, created.version()))
                .isInstanceOf(ObjectOptimisticLockingFailureException.class);
    }
}
