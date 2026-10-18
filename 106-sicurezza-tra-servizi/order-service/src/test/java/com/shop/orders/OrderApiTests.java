package com.shop.orders;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class OrderApiTests {
    static final String SERVICE_TOKEN = "order-service-test-token";
    static final HttpServer STUBS = stubs();

    @Autowired
    MockMvc mvc;

    @DynamicPropertySource
    static void stubAddresses(DynamicPropertyRegistry registry) {
        String base = "http://127.0.0.1:" + STUBS.getAddress().getPort();
        registry.add("spring.http.serviceclient.catalog.base-url", () -> base);
        registry.add("spring.security.oauth2.client.provider.keycloak.token-uri", () -> base + "/token");
        registry.add("spring.security.oauth2.client.registration.catalog-client.client-secret", () -> "test-only");
    }

    @AfterAll
    static void stopStubs() {
        STUBS.stop(0);
    }

    @Test
    void rejectsARequestWithoutToken() throws Exception {
        mvc.perform(post("/api/orders").contentType(MediaType.APPLICATION_JSON)
                        .content("{\"productId\":1,\"quantity\":2}"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void rejectsATokenWithoutTheOrdersScope() throws Exception {
        mvc.perform(post("/api/orders").with(jwt().authorities(new SimpleGrantedAuthority("SCOPE_catalog.read")))
                        .contentType(MediaType.APPLICATION_JSON).content("{\"productId\":1,\"quantity\":2}"))
                .andExpect(status().isForbidden());
    }

    @Test
    void callsTheCatalogWithItsOwnToken() throws Exception {
        mvc.perform(post("/api/orders").with(jwt().authorities(new SimpleGrantedAuthority("SCOPE_orders.write")))
                        .contentType(MediaType.APPLICATION_JSON).content("{\"productId\":1,\"quantity\":2}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.totalInCents").value(9800));
    }

    private static HttpServer stubs() {
        try {
            HttpServer server = HttpServer.create(new InetSocketAddress("127.0.0.1", 0), 0);
            server.createContext("/token", exchange -> {
                String token = "{\"access_token\":\"" + SERVICE_TOKEN + "\",\"token_type\":\"Bearer\",\"expires_in\":300}";
                send(exchange, 200, token);
            });
            server.createContext("/api/products/1", exchange -> {
                boolean authorized = ("Bearer " + SERVICE_TOKEN)
                        .equals(exchange.getRequestHeaders().getFirst("Authorization"));
                send(exchange, authorized ? 200 : 401,
                        authorized ? "{\"id\":1,\"name\":\"Keyboard\",\"priceInCents\":4900}" : "{}");
            });
            server.start();
            return server;
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private static void send(HttpExchange exchange, int status, String json) throws IOException {
        byte[] body = json.getBytes(StandardCharsets.UTF_8);
        exchange.getResponseHeaders().add("Content-Type", "application/json");
        exchange.sendResponseHeaders(status, body.length);
        exchange.getResponseBody().write(body);
        exchange.close();
    }
}
