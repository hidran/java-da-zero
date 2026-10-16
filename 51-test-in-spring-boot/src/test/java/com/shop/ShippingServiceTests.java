package com.shop;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ShippingServiceTests {
    private final ShippingService shipping = new ShippingService();

    @Test
    void chargesBelowThreshold() {
        assertEquals(490, shipping.fee(4999));
    }

    @Test
    void freeAtThreshold() {
        assertEquals(0, shipping.fee(5000));
    }

    @Test
    void rejectsNegativeSubtotal() {
        assertThrows(IllegalArgumentException.class, () -> shipping.fee(-1));
    }
}
