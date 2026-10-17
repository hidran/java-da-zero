package com.shop.catalog;

import static org.assertj.core.api.Assertions.assertThat;

import jakarta.persistence.EntityManagerFactory;
import org.hibernate.SessionFactory;
import org.hibernate.stat.Statistics;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.context.annotation.Import;

@DataJpaTest(properties = "spring.jpa.properties.hibernate.generate_statistics=true")
@Import(JpaProductRepository.class)
class ProductQueriesTests {
    @Autowired
    JpaProductRepository products;
    @Autowired
    EntityManagerFactory entityManagerFactory;
    Statistics statistics;

    @BeforeEach
    void resetStatistics() {
        statistics = entityManagerFactory.unwrap(SessionFactory.class).getStatistics();
        statistics.clear();
    }

    @Test
    void searchMatchesPartOfTheNameIgnoringCase() {
        assertThat(products.search("MO")).extracting(Product::name).containsExactly("Monitor", "Mouse");
    }

    @Test
    void priceRangeReturnsTheMatchingProducts() {
        assertThat(products.findInPriceRange(2000, 5000)).extracting(Product::name)
                .containsExactly("Mouse", "Webcam", "Keyboard");
    }

    @Test
    void priceRangeLoadsOnlyTheMatchingRows() {
        products.findInPriceRange(2000, 5000);
        assertThat(statistics.getEntityLoadCount()).isEqualTo(3);
    }

    @Test
    void priceListReadsNoEntities() {
        assertThat(products.priceList(5000)).containsExactly(
                new ProductSummary("Mouse", 2500),
                new ProductSummary("Webcam", 3900),
                new ProductSummary("Keyboard", 4900));
        assertThat(statistics.getEntityLoadCount()).isZero();
    }
}
