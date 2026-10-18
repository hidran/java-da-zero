package com.shop.gateway;

import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class GatewayRoutesTests {
    static final HttpServer CATALOG = stub("catalog");
    static final HttpServer ORDERS = stub("orders");

    @LocalServerPort
    int port;

    @DynamicPropertySource
    static void services(DynamicPropertyRegistry registry) {
        registry.add("shop.catalog-url", () -> "http://127.0.0.1:" + CATALOG.getAddress().getPort());
        registry.add("shop.orders-url", () -> "http://127.0.0.1:" + ORDERS.getAddress().getPort());
    }

    @AfterAll
    static void stopStubs() {
        CATALOG.stop(0);
        ORDERS.stop(0);
    }

    @Test
    void routesProductsToTheCatalog() throws Exception {
        HttpResponse<String> response = get("/api/products/1", null);
        assertThat(response.statusCode()).isEqualTo(200);
        assertThat(response.body()).startsWith("catalog GET /api/products/1");
    }

    @Test
    void routesOrdersToTheOrderService() throws Exception {
        HttpResponse<String> response = get("/api/orders/7", null);
        assertThat(response.statusCode()).isEqualTo(200);
        assertThat(response.body()).startsWith("orders GET /api/orders/7");
    }

    @Test
    void addsARequestIdWhenTheClientSendsNone() throws Exception {
        HttpResponse<String> response = get("/api/products/1", null);
        assertThat(response.statusCode()).isEqualTo(200);
        String requestId = response.headers().firstValue("X-Request-Id").orElse("");
        assertThat(requestId).hasSize(36);
        assertThat(response.body()).endsWith("request-id=" + requestId);
    }

    @Test
    void keepsTheRequestIdSentByTheClient() throws Exception {
        HttpResponse<String> response = get("/api/orders/7", "demo-42");
        assertThat(response.statusCode()).isEqualTo(200);
        assertThat(response.headers().firstValue("X-Request-Id")).hasValue("demo-42");
        assertThat(response.body()).endsWith("request-id=demo-42");
    }

    private HttpResponse<String> get(String path, String requestId) throws Exception {
        HttpRequest.Builder request = HttpRequest.newBuilder(URI.create("http://127.0.0.1:" + port + path));
        if (requestId != null) {
            request.header("X-Request-Id", requestId);
        }
        return HttpClient.newHttpClient().send(request.build(), HttpResponse.BodyHandlers.ofString());
    }

    private static HttpServer stub(String service) {
        try {
            HttpServer server = HttpServer.create(new InetSocketAddress("127.0.0.1", 0), 0);
            server.createContext("/", exchange -> {
                String text = service + " " + exchange.getRequestMethod() + " " + exchange.getRequestURI()
                        + " request-id=" + exchange.getRequestHeaders().getFirst("X-Request-Id");
                byte[] body = text.getBytes(StandardCharsets.UTF_8);
                exchange.sendResponseHeaders(200, body.length);
                exchange.getResponseBody().write(body);
                exchange.close();
            });
            server.start();
            return server;
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
