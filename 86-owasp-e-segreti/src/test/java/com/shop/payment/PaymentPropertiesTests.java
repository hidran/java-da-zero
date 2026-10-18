package com.shop.payment;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class PaymentPropertiesTests {
    @Test
    void toStringDoesNotRevealTheKey() {
        PaymentProperties properties = new PaymentProperties("test-only-value");
        assertThat(properties.toString()).doesNotContain("test-only-value");
        assertThat(properties.apiKey()).isEqualTo("test-only-value");
    }
}
