package com.shop.catalog;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;

@SpringBootTest
@ExtendWith(OutputCaptureExtension.class)
class PriceRuleTests {
    @Autowired
    ProductService service;

    @Test
    void createRejectsZeroPrice() {
        assertThrows(InvalidPriceException.class, () -> service.create("Mouse", 0));
    }

    @Test
    void changePriceRejectsZeroPrice() {
        assertThrows(InvalidPriceException.class, () -> service.changePrice(1, 0));
    }

    @Test
    void productRejectsZeroPrice() {
        assertThrows(InvalidPriceException.class, () -> new Product(1, "Mouse", 0));
    }

    @Test
    void validPriceIsAccepted() {
        assertEquals(2500, service.create("Mouse", 2500).priceInCents());
    }

    @Test
    void serviceCallsAreStillTimed(CapturedOutput output) {
        service.list();
        assertTrue(output.getOut().contains("ProductService.list() took"), "the timing aspect is gone");
    }
}
