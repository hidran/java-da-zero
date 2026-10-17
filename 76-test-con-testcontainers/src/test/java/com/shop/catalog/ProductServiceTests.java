package com.shop.catalog;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ProductServiceTests {
    @Autowired
    ProductService service;

    @Test
    void listStartsWithTheDemoProducts() {
        assertThat(service.list()).extracting(Product::name).startsWith("Keyboard", "Mouse", "Monitor");
    }

    @Test
    void createAssignsAnIdThatFindReturns() {
        Product created = service.create("Webcam", 3900);
        assertThat(created.id()).isPositive();
        assertThat(service.find(created.id())).isEqualTo(created);
    }

    @Test
    void findOfAMissingProductThrows() {
        assertThatThrownBy(() -> service.find(999)).isInstanceOf(ProductNotFoundException.class);
    }

    @Test
    void deleteRemovesTheProduct() {
        Product created = service.create("Lamp", 2900);
        service.delete(created.id());
        assertThatThrownBy(() -> service.find(created.id())).isInstanceOf(ProductNotFoundException.class);
    }
}
