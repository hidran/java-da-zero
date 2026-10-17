package com.shop.catalog;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;

@SpringBootTest
@ExtendWith(OutputCaptureExtension.class)
class ProductServiceLoggingTests {
    @Autowired
    ProductService service;

    @Test
    void listIsLogged(CapturedOutput output) {
        service.list();
        assertTrue(output.getOut().contains("Calling ProductService.list"), "list is not logged");
    }

    @Test
    void findIsLogged(CapturedOutput output) {
        service.find(1);
        assertTrue(output.getOut().contains("Calling ProductService.find"), "find is not logged");
    }

    @Test
    void createIsLogged(CapturedOutput output) {
        service.create("Webcam", 5900);
        assertTrue(output.getOut().contains("Calling ProductService.create"), "create is not logged");
    }

    @Test
    void deleteIsLogged(CapturedOutput output) {
        Product product = service.create("Headset", 7900);
        service.delete(product.id());
        assertTrue(output.getOut().contains("Calling ProductService.delete"), "delete is not logged");
    }

    @Test
    void serviceIsAProxy() {
        assertTrue(AopUtils.isAopProxy(service), "the service is not a proxy");
    }
}
