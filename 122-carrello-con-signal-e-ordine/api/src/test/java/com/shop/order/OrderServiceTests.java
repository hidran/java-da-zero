package com.shop.order;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.shop.catalog.ProductNotFoundException;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class OrderServiceTests {
    @Autowired
    OrderService service;

    @Autowired
    JdbcClient jdbc;

    @Test
    void placedOrderKeepsNamesAndPricesOfTheCatalog() {
        Order order = service.place(List.of(new OrderItem(1, 2), new OrderItem(2, 1)));
        assertThat(order.id()).isPositive();
        assertThat(order.status()).isEqualTo(OrderStatus.NEW);
        assertThat(order.lines()).extracting(OrderLine::productName).containsExactly("Keyboard", "Mouse");
        assertThat(order.totalInCents()).isEqualTo(12300);
    }

    @Test
    void orderWithAnUnknownProductLeavesNothingBehind() {
        long before = jdbc.sql("SELECT COUNT(*) FROM orders").query(Long.class).single();
        assertThatThrownBy(() -> service.place(List.of(new OrderItem(1, 1), new OrderItem(999, 1))))
                .isInstanceOf(ProductNotFoundException.class);
        long after = jdbc.sql("SELECT COUNT(*) FROM orders").query(Long.class).single();
        assertThat(after).isEqualTo(before);
    }
}
