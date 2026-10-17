package com.shop.monitoring;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.shop.catalog.ProductNotFoundException;
import com.shop.catalog.ProductService;
import java.util.regex.Pattern;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;

@SpringBootTest
@ExtendWith(OutputCaptureExtension.class)
class TimingAspectTests {
    @Autowired
    ProductService service;

    @Test
    void logsTheExecutionTime(CapturedOutput output) {
        service.list();
        Pattern line = Pattern.compile("ProductService\\.list\\(\\) took \\d+ ms");
        assertTrue(line.matcher(output.getOut()).find(), "no timing line for list");
    }

    @Test
    void keepsTheReturnValue() {
        assertEquals("Keyboard", service.find(1).name());
    }

    @Test
    void logsTheTimeEvenWhenTheMethodFails(CapturedOutput output) {
        assertThrows(ProductNotFoundException.class, () -> service.find(99));
        assertTrue(output.getOut().contains("ProductService.find(..) took"), "no timing line for a failed find");
    }
}
