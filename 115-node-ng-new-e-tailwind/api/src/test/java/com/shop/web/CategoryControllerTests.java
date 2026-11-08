package com.shop.web;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import com.shop.catalog.Category;
import com.shop.catalog.CategoryService;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.assertj.MockMvcTester;

@WebMvcTest(CategoryController.class)
@AutoConfigureMockMvc(addFilters = false)
class CategoryControllerTests {
    @Autowired
    MockMvcTester mvc;

    @MockitoBean
    CategoryService service;

    @Test
    void listsTheCategories() {
        given(service.list()).willReturn(List.of(new Category(3, "Audio"), new Category(2, "Displays")));
        assertThat(mvc.get().uri("/api/categories"))
                .hasStatusOk()
                .bodyJson()
                .hasPathSatisfying("$.length()", value -> assertThat(value).isEqualTo(2))
                .hasPathSatisfying("$[0].name", value -> assertThat(value).isEqualTo("Audio"));
    }
}
