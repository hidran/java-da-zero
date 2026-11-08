package com.shop.web;

import com.shop.catalog.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Categories", description = "Read the product categories")
@RestController
public class CategoryController {
    private final CategoryService service;

    public CategoryController(CategoryService service) {
        this.service = service;
    }

    @Operation(summary = "List the categories by name")
    @GetMapping("/api/categories")
    public List<CategoryResponse> list() {
        return service.list().stream().map(CategoryResponse::from).toList();
    }
}
