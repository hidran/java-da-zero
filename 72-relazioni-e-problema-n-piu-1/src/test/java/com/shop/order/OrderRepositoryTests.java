package com.shop.order;

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
@Import(JpaOrderRepository.class)
class OrderRepositoryTests {
    @Autowired
    JpaOrderRepository orders;
    @Autowired
    EntityManagerFactory entityManagerFactory;
    Statistics statistics;

    @BeforeEach
    void resetStatistics() {
        statistics = entityManagerFactory.unwrap(SessionFactory.class).getStatistics();
        statistics.clear();
    }

    @Test
    void findAllMapsOrdersWithTheirLines() {
        assertThat(orders.findAll()).extracting(Order::totalInCents).containsExactly(9900, 22800, 132400);
    }

    @Test
    void findAllUsesASingleQuery() {
        orders.findAll();
        assertThat(statistics.getPrepareStatementCount()).isEqualTo(1);
    }
}
