package com.shop.web;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import com.shop.catalog.Category;
import com.shop.catalog.Product;
import com.shop.catalog.ProductNotFoundException;
import com.shop.catalog.ProductPage;
import com.shop.catalog.ProductService;
import com.shop.catalog.ProductSort;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.assertj.MockMvcTester;

// Controller slice: the security rules are tested in SecurityRulesTests, here the filters are off.
@WebMvcTest(ProductController.class)
@AutoConfigureMockMvc(addFilters = false)
class ProductControllerTests {
    private static final Category PERIPHERALS = new Category(1, "Peripherals");
    private static final Product KEYBOARD = new Product(1, "Keyboard", "Compact keyboard.", 4900, PERIPHERALS);

    @Autowired
    MockMvcTester mvc;

    @MockitoBean
    ProductService service;

    @Test
    void listPassesFiltersAndPageToTheService() {
        given(service.search("key", 1L, ProductSort.PRICE, 0, 12))
                .willReturn(new ProductPage(List.of(KEYBOARD), 0, 12, 1));
        assertThat(mvc.get().uri("/api/products?name=key&categoryId=1&sort=PRICE"))
                .hasStatusOk()
                .bodyJson()
                .hasPathSatisfying("$.totalPages", value -> assertThat(value).isEqualTo(1))
                .hasPathSatisfying("$.content[0].categoryName", value -> assertThat(value).isEqualTo("Peripherals"));
    }

    @Test
    void emptyCategoryMeansEveryCategory() {
        given(service.search(any(), isNull(), any(), anyInt(), anyInt()))
                .willReturn(new ProductPage(List.of(), 0, 12, 0));
        assertThat(mvc.get().uri("/api/products?categoryId=")).hasStatusOk();
        verify(service).search("", null, ProductSort.ID, 0, 12);
    }

    @Test
    void readsOneProduct() {
        given(service.find(1)).willReturn(KEYBOARD);
        assertThat(mvc.get().uri("/api/products/1"))
                .hasStatusOk()
                .bodyJson().extractingPath("$.priceInCents").isEqualTo(4900);
    }

    @Test
    void missingProductBecomesAProblemDetail() {
        given(service.find(99)).willThrow(new ProductNotFoundException(99));
        assertThat(mvc.get().uri("/api/products/99"))
                .hasStatus(HttpStatus.NOT_FOUND)
                .hasContentType("application/problem+json")
                .bodyJson().extractingPath("$.detail").isEqualTo("Product 99 does not exist.");
    }

    @Test
    void rejectsAPageThatIsTooLarge() {
        assertThat(mvc.get().uri("/api/products?size=100"))
                .hasStatus(HttpStatus.BAD_REQUEST)
                .hasContentType("application/problem+json");
    }
}
