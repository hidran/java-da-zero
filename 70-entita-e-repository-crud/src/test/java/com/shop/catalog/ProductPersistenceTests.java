package com.shop.catalog;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.simple.JdbcClient;

@SpringBootTest
class ProductPersistenceTests {
    @Autowired
    ProductService service;
    @Autowired
    JdbcClient jdbc;

    @Test
    void createdProductIsStoredInTheProductTable() {
        service.create("Headset", 5900);
        long rows = jdbc.sql("SELECT COUNT(*) FROM product WHERE name = 'Headset'").query(Long.class).single();
        assertThat(rows).isEqualTo(1);
    }
}
