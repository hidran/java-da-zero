package com.shop.orders;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@SpringBootTest
@AutoConfigureMockMvc
class OrderApiTests {
    static HttpServer catalog;
    static final AtomicInteger calls = new AtomicInteger();
    static volatile int failuresLeft;
    static volatile long delayMillis;

    @Autowired
    MockMvc mvc;

    @MockitoBean
    MessageSender sender;

    @DynamicPropertySource
    static void catalogStub(DynamicPropertyRegistry registry) throws IOException {
        catalog = HttpServer.create(new InetSocketAddress("127.0.0.1", 0), 0);
        catalog.setExecutor(Executors.newCachedThreadPool());
        catalog.createContext("/api/products/", OrderApiTests::answer);
        catalog.start();
        registry.add("spring.http.serviceclient.catalog.base-url",
                () -> "http://127.0.0.1:" + catalog.getAddress().getPort());
    }

    @AfterAll
    static void stopCatalog() {
        catalog.stop(0);
    }

    @BeforeEach
    void healthyCatalog() {
        calls.set(0);
        failuresLeft = 0;
        delayMillis = 0;
    }

    @Test
    void copiesNameAndPriceFromTheCatalog() throws Exception {
        placeOrder(1).andExpect(status().isCreated())
                .andExpect(jsonPath("$.productName").value("Keyboard"))
                .andExpect(jsonPath("$.totalInCents").value(9800));
    }

    @Test
    void reportsAProductTheCatalogDoesNotKnow() throws Exception {
        placeOrder(99).andExpect(status().isNotFound());
    }

    @Test
    void answersQuicklyWhenTheCatalogIsTooSlow() throws Exception {
        delayMillis = 2000;
        placeOrder(1).andExpect(status().isServiceUnavailable())
                .andExpect(jsonPath("$.type").value("https://api.shop.example/problems/catalog-unavailable"));
    }

    @Test
    void retriesATemporaryFailure() throws Exception {
        failuresLeft = 1;
        placeOrder(1).andExpect(status().isCreated());
        assertThat(calls.get()).isEqualTo(2);
    }

    private ResultActions placeOrder(long productId) throws Exception {
        return mvc.perform(post("/api/orders").contentType(MediaType.APPLICATION_JSON)
                .content("{\"productId\":" + productId + ",\"quantity\":2}"));
    }

    private static void answer(HttpExchange exchange) throws IOException {
        calls.incrementAndGet();
        try {
            Thread.sleep(delayMillis);
        } catch (InterruptedException exception) {
            Thread.currentThread().interrupt();
        }
        if (failuresLeft > 0) {
            failuresLeft--;
            exchange.sendResponseHeaders(503, -1);
        } else if (exchange.getRequestURI().getPath().endsWith("/1")) {
            byte[] body = "{\"id\":1,\"name\":\"Keyboard\",\"priceInCents\":4900}".getBytes(StandardCharsets.UTF_8);
            exchange.getResponseHeaders().add("Content-Type", "application/json");
            exchange.sendResponseHeaders(200, body.length);
            exchange.getResponseBody().write(body);
        } else {
            exchange.sendResponseHeaders(404, -1);
        }
        exchange.close();
    }
}
