package com.shop.audit;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.shop.catalog.Product;
import com.shop.catalog.ProductNotFoundException;
import com.shop.catalog.ProductService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;

@SpringBootTest
@ExtendWith(OutputCaptureExtension.class)
class AuditAspectTests {
    @Autowired
    ProductService service;

    @Test
    void createIsAudited(CapturedOutput output) {
        service.create("Webcam", 5900);
        assertTrue(output.getOut().contains("AUDIT product-created [Webcam, 5900]"), "create is not audited");
    }

    @Test
    void deleteIsAudited(CapturedOutput output) {
        Product product = service.create("Headset", 7900);
        service.delete(product.id());
        assertTrue(output.getOut().contains("AUDIT product-deleted [" + product.id() + "]"), "delete is not audited");
    }

    @Test
    void readsAreNotAudited(CapturedOutput output) {
        service.list();
        service.find(1);
        assertFalse(output.getOut().contains("AUDIT"), "a read was audited");
    }

    @Test
    void failedDeleteIsNotAudited(CapturedOutput output) {
        assertThrows(ProductNotFoundException.class, () -> service.delete(99));
        assertFalse(output.getOut().contains("AUDIT"), "a failed delete was audited");
    }
}
