package com.shop.web;

import com.shop.catalog.ProductService;
import com.shop.catalog.ProductSort;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Products", description = "Read the product catalog")
@RestController
@RequestMapping("/api/products")
public class ProductController {
    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    @Operation(summary = "List products, one page at a time")
    @GetMapping
    public ProductPageResponse list(
            @RequestParam(defaultValue = "") String name,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(defaultValue = "ID") ProductSort sort,
            @RequestParam(defaultValue = "0") @Min(0) int page,
            @RequestParam(defaultValue = "12") @Min(1) @Max(48) int size) {
        return ProductPageResponse.from(service.search(name, categoryId, sort, page, size));
    }

    @Operation(summary = "Read one product")
    @ApiResponse(responseCode = "200", description = "The product")
    @ApiResponse(responseCode = "404", description = "Product not found",
            content = @Content(mediaType = "application/problem+json",
                    schema = @Schema(implementation = ProblemDetail.class)))
    @GetMapping("/{id}")
    public ProductResponse find(@PathVariable long id) {
        return ProductResponse.from(service.find(id));
    }
}
