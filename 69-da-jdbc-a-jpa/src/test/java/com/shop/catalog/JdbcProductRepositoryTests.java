package com.shop.catalog;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.test.autoconfigure.JdbcTest;
import org.springframework.context.annotation.Import;

@JdbcTest
@Import(JdbcProductRepository.class)
class JdbcProductRepositoryTests {
    @Autowired
    JdbcProductRepository repository;

    @Test
    void findAllMapsEveryRow() {
        assertThat(repository.findAll()).containsExactly(
                new Product(1, "Keyboard", 4900),
                new Product(2, "Mouse", 2500),
                new Product(3, "Monitor", 18900));
    }

    @Test
    void findByIdMapsOneRow() {
        assertThat(repository.findById(2)).contains(new Product(2, "Mouse", 2500));
    }

    @Test
    void findByIdOfAMissingRowIsEmpty() {
        assertThat(repository.findById(999)).isEmpty();
    }

    @Test
    void saveReturnsTheGeneratedId() {
        Product saved = repository.save(new Product(0, "Webcam", 3900));
        assertThat(saved.id()).isPositive();
        assertThat(repository.findAll()).contains(saved);
    }

    @Test
    void deleteByIdRemovesTheRow() {
        assertThat(repository.deleteById(3)).isTrue();
        assertThat(repository.deleteById(3)).isFalse();
    }
}
