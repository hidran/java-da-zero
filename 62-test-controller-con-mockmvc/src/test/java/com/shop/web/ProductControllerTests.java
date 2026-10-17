package com.shop.web;

import com.shop.catalog.Product;
import com.shop.catalog.ProductNotFoundException;
import com.shop.catalog.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.assertj.MockMvcTester;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verifyNoInteractions;

@WebMvcTest(ProductController.class)
class ProductControllerTests {
    @Autowired MockMvcTester mvc;
    @MockitoBean ProductService service;

    @Test
    void readsAProductFromTheService() {
        given(service.find(1)).willReturn(new Product(1, "Keyboard", 4900));
        assertThat(mvc.get().uri("/api/products/1"))
                .hasStatusOk()
                .bodyJson().extractingPath("$.name").isEqualTo("Keyboard");
    }

    @Test
    void missingProductBecomesAProblemDetail() {
        given(service.find(99)).willThrow(new ProductNotFoundException(99));
        assertThat(mvc.get().uri("/api/products/99"))
                .hasStatus(HttpStatus.NOT_FOUND)
                .hasContentType("application/problem+json")
                .bodyJson().extractingPath("$.title").isEqualTo("Product not found");
    }

    @Test
    void invalidProductNeverReachesTheService() {
        assertThat(mvc.post().uri("/api/products").contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\" \",\"priceInCents\":0}"))
                .hasStatus(HttpStatus.BAD_REQUEST);
        verifyNoInteractions(service);
    }
}
