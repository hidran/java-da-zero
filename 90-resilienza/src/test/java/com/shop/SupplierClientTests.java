package com.shop;

import com.shop.stock.SupplierClient;
import com.shop.supplier.FakeSupplierController;
import java.time.Duration;
import java.time.Instant;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
class SupplierClientTests {
    @Autowired SupplierClient client;
    @Autowired FakeSupplierController supplier;

    @Test
    void answersWithTheStock() {
        assertThat(client.stock(1).available()).isEqualTo(12);
    }

    @Test
    void flakySupplierIsRetried() {
        assertThat(client.stock(3).available()).isEqualTo(12);
        assertThat(supplier.calls(3)).isEqualTo(3);
    }

    @Test
    void failingSupplierGivesUpAfterThreeAttempts() {
        assertThatThrownBy(() -> client.stock(4)).isInstanceOf(HttpServerErrorException.class);
        assertThat(supplier.calls(4)).isEqualTo(3);
    }

    @Test
    void slowSupplierTimesOut() {
        Instant start = Instant.now();
        assertThatThrownBy(() -> client.stock(2)).isInstanceOf(ResourceAccessException.class);
        assertThat(Duration.between(start, Instant.now())).isLessThan(Duration.ofSeconds(2));
    }
}
