package com.shop.orders;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class OrderServiceTests {
    @Autowired
    OrderService orders;

    @Test
    void computesTheTotalFromTheCatalogPrice() {
        Order order = orders.place(1L, 2);
        assertThat(order.productName()).isEqualTo("Keyboard");
        assertThat(order.totalInCents()).isEqualTo(9800);
    }

    @Test
    void rejectsAnUnknownProduct() {
        assertThatThrownBy(() -> orders.place(99L, 1))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Unknown product 99");
    }
}
