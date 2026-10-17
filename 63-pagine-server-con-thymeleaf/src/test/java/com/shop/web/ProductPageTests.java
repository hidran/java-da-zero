package com.shop.web;

import com.shop.catalog.Product;
import com.shop.catalog.ProductService;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.assertj.MockMvcTester;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.verifyNoInteractions;

@WebMvcTest(ProductPageController.class)
class ProductPageTests {
    @Autowired MockMvcTester mvc;
    @MockitoBean ProductService service;

    @Test
    void listPageShowsTheProducts() {
        given(service.list()).willReturn(List.of(new Product(1, "Keyboard", 4900)));
        assertThat(mvc.get().uri("/products"))
                .hasStatusOk().hasViewName("products/list")
                .bodyText().contains("Keyboard");
    }

    @Test
    void formPageStartsEmpty() {
        assertThat(mvc.get().uri("/products/new"))
                .hasStatusOk().hasViewName("products/form")
                .bodyText().contains("name=\"priceInCents\"");
    }

    @Test
    void validFormRedirectsToTheList() {
        given(service.create("Mouse", 2500)).willReturn(new Product(2, "Mouse", 2500));
        assertThat(mvc.post().uri("/products")
                .param("name", "Mouse").param("priceInCents", "2500"))
                .hasStatus3xxRedirection().hasRedirectedUrl("/products");
        then(service).should().create("Mouse", 2500);
    }

    @Test
    void invalidFormShowsTheErrors() {
        assertThat(mvc.post().uri("/products")
                .param("name", " ").param("priceInCents", "0"))
                .hasStatusOk().hasViewName("products/form")
                .bodyText().contains("must not be blank")
                .contains("must be greater than 0");
        verifyNoInteractions(service);
    }
}
