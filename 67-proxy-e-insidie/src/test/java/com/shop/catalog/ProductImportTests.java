package com.shop.catalog;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;

@SpringBootTest
@ExtendWith(OutputCaptureExtension.class)
class ProductImportTests {
    @Autowired
    ProductService service;

    @Autowired
    ProductImporter importer;

    @Test
    void createIsAudited(CapturedOutput output) {
        service.create("Mouse", 2500);
        assertTrue(output.getOut().contains("AUDIT product-created [Mouse, 2500]"), "create is not audited");
    }

    @Test
    void importAuditsEveryProduct(CapturedOutput output) {
        importer.importAll(List.of(new ProductDraft("Webcam", 5900), new ProductDraft("Headset", 7900)));
        assertTrue(output.getOut().contains("AUDIT product-created [Webcam, 5900]"), "Webcam is not audited");
        assertTrue(output.getOut().contains("AUDIT product-created [Headset, 7900]"), "Headset is not audited");
    }

    @Test
    void serviceIsAProxySubclass() {
        assertTrue(AopUtils.isCglibProxy(service), "the service is not a CGLIB proxy");
        assertNotEquals(ProductService.class, service.getClass());
    }
}
