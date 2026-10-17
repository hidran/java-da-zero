package com.shop.order;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.shop.catalog.ProductNotFoundException;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.simple.JdbcClient;

@SpringBootTest
class OrderServiceTests {
    @Autowired
    OrderService service;
    @Autowired
    JdbcClient jdbc;

    @Test
    void placedOrderIsReadWithItsLines() {
        long id = service.place(List.of(new OrderItem(1, 2), new OrderItem(2, 1)));
        Order order = service.find(id);
        assertThat(order.status()).isEqualTo(OrderStatus.NEW);
        assertThat(order.totalInCents()).isEqualTo(12300);
    }

    @Test
    void failedOrderLeavesNothingBehind() {
        long ordersBefore = count("orders");
        long linesBefore = count("order_line");
        assertThatThrownBy(() -> service.place(List.of(new OrderItem(1, 1), new OrderItem(999, 1))))
                .isInstanceOf(ProductNotFoundException.class);
        assertThat(count("orders")).isEqualTo(ordersBefore);
        assertThat(count("order_line")).isEqualTo(linesBefore);
    }

    private long count(String table) {
        return jdbc.sql("SELECT COUNT(*) FROM " + table).query(Long.class).single();
    }
}
