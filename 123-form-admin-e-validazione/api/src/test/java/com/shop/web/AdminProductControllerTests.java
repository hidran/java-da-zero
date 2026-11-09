package com.shop.web;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verifyNoInteractions;

import com.shop.catalog.Category;
import com.shop.catalog.CategoryNotFoundException;
import com.shop.catalog.DuplicateProductNameException;
import com.shop.catalog.Product;
import com.shop.catalog.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.assertj.MockMvcTester;

@WebMvcTest(AdminProductController.class)
@AutoConfigureMockMvc(addFilters = false)
class AdminProductControllerTests {
    private static final String LAMP = """
            {"name":"Desk lamp XL","description":"A larger lamp.","priceInCents":4400,"categoryId":4}
            """;
    private static final Product SAVED = new Product(31, "Desk lamp XL", "A larger lamp.", 4400, new Category(4, "Desk"));

    @Autowired
    MockMvcTester mvc;

    @MockitoBean
    ProductService service;

    @Test
    void createAnswersWithTheLocationOfTheProduct() {
        given(service.create("Desk lamp XL", "A larger lamp.", 4400, 4)).willReturn(SAVED);
        assertThat(mvc.post().uri("/api/admin/products").contentType(MediaType.APPLICATION_JSON).content(LAMP))
                .hasStatus(HttpStatus.CREATED)
                .hasHeader("Location", "/api/products/31")
                .bodyJson().extractingPath("$.categoryName").isEqualTo("Desk");
    }

    @Test
    void invalidProductListsEveryFieldError() {
        String invalid = """
                {"name":" ","description":"","priceInCents":0,"categoryId":0}
                """;
        assertThat(mvc.post().uri("/api/admin/products").contentType(MediaType.APPLICATION_JSON).content(invalid))
                .hasStatus(HttpStatus.BAD_REQUEST)
                .bodyJson()
                .hasPathSatisfying("$.errors.name", value -> assertThat(value).isEqualTo("Name is required"))
                .hasPathSatisfying("$.errors.priceInCents",
                        value -> assertThat(value).isEqualTo("Price must be greater than zero"))
                .hasPathSatisfying("$.errors.categoryId", value -> assertThat(value).isEqualTo("Choose a category"));
        verifyNoInteractions(service);
    }

    @Test
    void duplicateNameIsAFieldError() {
        given(service.create("Desk lamp XL", "A larger lamp.", 4400, 4))
                .willThrow(new DuplicateProductNameException("Desk lamp XL"));
        assertThat(mvc.post().uri("/api/admin/products").contentType(MediaType.APPLICATION_JSON).content(LAMP))
                .hasStatus(HttpStatus.BAD_REQUEST)
                .bodyJson().extractingPath("$.errors.name").isEqualTo("A product named Desk lamp XL already exists.");
    }

    @Test
    void unknownCategoryIsAFieldError() {
        given(service.create("Desk lamp XL", "A larger lamp.", 4400, 4)).willThrow(new CategoryNotFoundException(4));
        assertThat(mvc.post().uri("/api/admin/products").contentType(MediaType.APPLICATION_JSON).content(LAMP))
                .hasStatus(HttpStatus.BAD_REQUEST)
                .bodyJson().extractingPath("$.errors.categoryId").isEqualTo("Category 4 does not exist.");
    }
}
