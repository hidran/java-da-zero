package com.shop.catalog;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ProductServiceTests {
    private final ProductService service = new ProductService(new InMemoryProductRepository());

    @Test
    void createAssignsTheNextId() {
        Product product = service.create("Webcam", 5900);
        assertThat(product.id()).isEqualTo(4);
        assertThat(service.find(4)).isEqualTo(product);
    }

    @Test
    void unknownProductIsReported() {
        assertThatThrownBy(() -> service.find(99))
                .isInstanceOf(ProductNotFoundException.class)
                .hasMessage("Product 99 does not exist.");
    }

    @Test
    void statsSumTheWholeCatalog() {
        assertThat(service.stats()).isEqualTo(new CatalogStats(3, 26300));
    }
}
