package com.shop.catalog;

import static org.assertj.core.api.Assertions.assertThat;

import java.sql.Connection;
import java.sql.SQLException;
import javax.sql.DataSource;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Import;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.postgresql.PostgreSQLContainer;

@DataJpaTest
@Import(JpaProductRepository.class)
@Testcontainers(disabledWithoutDocker = true)
class PostgresProductRepositoryTests {
    @Container
    @ServiceConnection
    static PostgreSQLContainer postgres = new PostgreSQLContainer("postgres:18.6-alpine");
    @Autowired
    JpaProductRepository products;
    @Autowired
    DataSource dataSource;

    @Test
    void runsOnPostgres() throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            assertThat(connection.getMetaData().getDatabaseProductName()).isEqualTo("PostgreSQL");
        }
    }

    @Test
    void migrationsCreatedTheDemoProducts() {
        assertThat(products.findAll()).extracting(Product::name)
                .containsExactly("Keyboard", "Mouse", "Monitor");
    }

    @Test
    void savedProductGetsAnIdFromTheDatabase() {
        Product saved = products.save(new Product(0, "Webcam", 3900));
        assertThat(saved.id()).isPositive();
        assertThat(products.findById(saved.id())).contains(saved);
    }
}
