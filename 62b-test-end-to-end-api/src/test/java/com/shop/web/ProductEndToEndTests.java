package com.shop.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureRestTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.test.web.servlet.client.RestTestClient;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureRestTestClient
class ProductEndToEndTests {
    @Autowired RestTestClient client;

    @Test
    void createdProductCanBeReadBack() {
        ProductResponse created = client.post().uri("/api/products")
                .body(new CreateProductRequest("Webcam HD", 6900))
                .exchange()
                .expectStatus().isCreated()
                .expectBody(ProductResponse.class).returnResult().getResponseBody();
        client.get().uri("/api/products/" + created.id())
                .exchange()
                .expectStatus().isOk()
                .expectBody().jsonPath("$.name").isEqualTo("Webcam HD");
    }

    @Test
    void unknownProductIsAProblemDetail() {
        ProblemDetail problem = client.get().uri("/api/products/999")
                .exchange()
                .expectStatus().isNotFound()
                .expectHeader().contentType(MediaType.APPLICATION_PROBLEM_JSON)
                .expectBody(ProblemDetail.class).returnResult().getResponseBody();
        assertThat(problem.getTitle()).isEqualTo("Product not found");
        assertThat(problem.getType())
                .hasToString("https://api.shop.example/problems/product-not-found");
    }

    @Test
    void invalidProductIsRejected() {
        client.post().uri("/api/products")
                .body(new CreateProductRequest(" ", 0))
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody()
                .jsonPath("$.errors.name").exists()
                .jsonPath("$.errors.priceInCents").exists();
    }
}
