package com.shop.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

class ProductTest {
    @Test
    void rejectsNegativePrice() {
        assertThatThrownBy(() -> new Product("Mug", -1))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
