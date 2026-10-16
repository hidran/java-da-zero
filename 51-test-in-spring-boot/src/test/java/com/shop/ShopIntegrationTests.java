package com.shop;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class ShopIntegrationTests {
    @Autowired
    QuoteController controller;

    @Test
    void wiresControllerToRealShippingService() {
        assertEquals(490, controller.quote(2000));
        assertEquals(0, controller.quote(5000));
    }
}
