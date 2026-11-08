package com.shop.catalog;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class CatalogPersistenceTests {
    @Autowired
    ProductRepository products;

    @Autowired
    CategoryRepository categories;

    @Autowired
    JdbcClient jdbc;

    @Test
    void flywayAppliedEveryMigration() {
        var versions = jdbc.sql("""
                SELECT "version" FROM "flyway_schema_history"
                WHERE "type" = 'SQL' ORDER BY "installed_rank"
                """).query(String.class).list();
        assertThat(versions).containsExactly("1", "2", "3", "4");
    }

    // ShopEndToEndTests may add products to the shared in-memory database: count only the ids of the seed.
    @Test
    void seedHasThirtyProductsInFiveCategories() {
        assertThat(products.findAll()).filteredOn(product -> product.id() <= 30).hasSize(30);
        assertThat(categories.findAll()).extracting(Category::name)
                .containsExactly("Audio", "Cables and power", "Desk", "Displays", "Peripherals");
    }

    @Test
    void productsComeWithTheirCategory() {
        Product keyboard = products.findById(1).orElseThrow();
        assertThat(keyboard.name()).isEqualTo("Keyboard");
        assertThat(keyboard.category().name()).isEqualTo("Peripherals");
    }

    @Test
    void savedProductGetsAnIdFromTheDatabase() {
        Product saved = products.save(new Product(0, "Webcam HD", "Full HD.", 6900, new Category(1, "Peripherals")));
        assertThat(saved.id()).isPositive();
        assertThat(products.findById(saved.id())).contains(saved);
    }

    @Test
    void nameCheckIgnoresCase() {
        assertThat(products.existsByName("keyboard")).isTrue();
        assertThat(products.existsByName("Webcam 8K")).isFalse();
    }

    @Test
    void passwordsAreStoredOnlyAsHashes() {
        var hashes = jdbc.sql("SELECT password_hash FROM app_user").query(String.class).list();
        assertThat(hashes).hasSize(2).allMatch(hash -> hash.startsWith("{bcrypt}"));
    }
}
