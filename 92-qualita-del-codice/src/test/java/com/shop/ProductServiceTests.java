package com.shop;

import com.shop.catalog.InMemoryProductRepository;
import com.shop.catalog.Product;
import com.shop.catalog.ProductNotFoundException;
import com.shop.catalog.ProductService;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ProductServiceTests {
    private final ProductService service = new ProductService(new InMemoryProductRepository());

    @Test
    void listReturnsTheDemoProducts() {
        assertThat(service.list()).extracting(Product::name).containsExactly("Keyboard", "Mouse", "Monitor");
    }

    @Test
    void createAssignsTheNextId() {
        assertThat(service.create("Webcam", 3900).id()).isEqualTo(4);
    }

    @Test
    void findingAnUnknownProductFails() {
        assertThatThrownBy(() -> service.find(99)).isInstanceOf(ProductNotFoundException.class);
    }
}
