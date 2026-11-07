package com.shop.order;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ShippingPolicyTests {
    private final ShippingPolicy policy = new ShippingPolicy();

    @Test
    void largeOrder() {
        assertThat(policy.feeInCents(8000)).isEqualTo(0);
    }

    @Test
    void mediumOrder() {
        assertThat(policy.feeInCents(4000)).isEqualTo(200);
    }

    @Test
    void smallOrder() {
        assertThat(policy.feeInCents(1000)).isEqualTo(500);
    }

    @Test
    void negativeTotal() {
        assertThatThrownBy(() -> policy.feeInCents(-1));
    }

    @Test
    void boundariesFromBothSides() {
        assertThat(policy.feeInCents(5000)).isEqualTo(0);
        assertThat(policy.feeInCents(4999)).isEqualTo(200);
        assertThat(policy.feeInCents(3000)).isEqualTo(200);
        assertThat(policy.feeInCents(2999)).isEqualTo(500);
        assertThat(policy.feeInCents(0)).isEqualTo(500);
    }
}
