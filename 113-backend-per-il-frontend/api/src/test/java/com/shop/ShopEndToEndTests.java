package com.shop;

import static org.assertj.core.api.Assertions.assertThat;

import com.jayway.jsonpath.JsonPath;
import com.shop.web.OrderResponse;
import com.shop.web.ProductResponse;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureRestTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.client.RestTestClient;

// The whole application on a random port, called over real HTTP, as the browser does.
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureRestTestClient
@ActiveProfiles("test")
class ShopEndToEndTests {
    @Autowired
    RestTestClient client;

    @Test
    void guestFindsAProductAndOrdersIt() {
        client.get().uri("/api/products?name=mouse&sort=PRICE")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.totalElements").isEqualTo(2)
                .jsonPath("$.content[0].name").isEqualTo("Mouse pad");
        client.get().uri("/api/products/2")
                .exchange()
                .expectStatus().isOk()
                .expectBody().jsonPath("$.categoryName").isEqualTo("Peripherals");
        OrderResponse order = client.post().uri("/api/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .body(Map.of("lines", List.of(Map.of("productId", 2, "quantity", 3))))
                .exchange()
                .expectStatus().isCreated()
                .expectBody(OrderResponse.class).returnResult().getResponseBody();
        assertThat(order.totalInCents()).isEqualTo(7500);
    }

    @Test
    void adminLogsInAndAddsAProduct() {
        String json = client.post().uri("/api/auth/token")
                .headers(headers -> headers.setBasicAuth("admin", "admin-demo"))
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class).returnResult().getResponseBody();
        String token = JsonPath.read(json, "$.token");
        client.get().uri("/api/me")
                .headers(headers -> headers.setBearerAuth(token))
                .exchange()
                .expectStatus().isOk()
                .expectBody().jsonPath("$.roles[0]").isEqualTo("ADMIN");
        ProductResponse created = client.post().uri("/api/admin/products")
                .headers(headers -> headers.setBearerAuth(token))
                .contentType(MediaType.APPLICATION_JSON)
                .body(Map.of("name", "Webcam 4K", "description", "Sharp webcam.", "priceInCents", 9900, "categoryId", 1))
                .exchange()
                .expectStatus().isCreated()
                .expectBody(ProductResponse.class).returnResult().getResponseBody();
        client.get().uri("/api/products/" + created.id())
                .exchange()
                .expectStatus().isOk()
                .expectBody().jsonPath("$.name").isEqualTo("Webcam 4K");
    }

    @Test
    void customerCannotChangeTheCatalog() {
        String json = client.post().uri("/api/auth/token")
                .headers(headers -> headers.setBasicAuth("customer", "customer-demo"))
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class).returnResult().getResponseBody();
        String token = JsonPath.read(json, "$.token");
        client.post().uri("/api/admin/products")
                .headers(headers -> headers.setBearerAuth(token))
                .contentType(MediaType.APPLICATION_JSON)
                .body(Map.of("name", "Cheap keyboard", "description", "Cheaper.", "priceInCents", 100, "categoryId", 1))
                .exchange()
                .expectStatus().isForbidden();
    }

    @Test
    void unknownProductIsAProblemDetail() {
        client.get().uri("/api/products/999")
                .exchange()
                .expectStatus().isNotFound()
                .expectHeader().contentType(MediaType.APPLICATION_PROBLEM_JSON)
                .expectBody().jsonPath("$.title").isEqualTo("Product not found");
    }
}
