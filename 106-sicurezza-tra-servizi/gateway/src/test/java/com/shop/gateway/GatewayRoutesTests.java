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
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Bean;
import org.springframework.security.oauth2.jwt.BadJwtException;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class GatewayRoutesTests {
    static final String ALICE_TOKEN = "alice-test-token";
    static final HttpServer SERVICES = stub();

    @LocalServerPort
    int port;

    @TestConfiguration
    static class MockTokens {
        @Bean
        JwtDecoder jwtDecoder() {
            return token -> {
                if (!ALICE_TOKEN.equals(token)) {
                    throw new BadJwtException("Unknown test token");
                }
                return Jwt.withTokenValue(token).header("alg", "none").subject("alice")
                        .claim("scope", "catalog.read orders.write").build();
            };
        }
    }

    @DynamicPropertySource
    static void services(DynamicPropertyRegistry registry) {
        String base = "http://127.0.0.1:" + SERVICES.getAddress().getPort();
        registry.add("shop.catalog-url", () -> base);
        registry.add("shop.orders-url", () -> base);
        registry.add("shop.keycloak-url", () -> base);
    }

    @AfterAll
    static void stopStub() {
        SERVICES.stop(0);
    }

    @Test
    void rejectsAnApiCallWithoutToken() throws Exception {
        assertThat(get("/api/products/1", null).statusCode()).isEqualTo(401);
    }

    @Test
    void rejectsAnUnknownToken() throws Exception {
        assertThat(get("/api/products/1", "forged-token").statusCode()).isEqualTo(401);
    }

    @Test
    void relaysTheUserTokenToTheService() throws Exception {
        HttpResponse<String> response = get("/api/orders/1", ALICE_TOKEN);
        assertThat(response.statusCode()).isEqualTo(200);
        assertThat(response.body()).isEqualTo("GET /api/orders/1 authorization=Bearer " + ALICE_TOKEN);
    }

    @Test
    void letsTheTokenRequestReachTheIdentityProvider() throws Exception {
        HttpResponse<String> response = get("/realms/shop/protocol/openid-connect/certs", null);
        assertThat(response.statusCode()).isEqualTo(200);
        assertThat(response.body()).startsWith("GET /realms/shop");
    }

    private HttpResponse<String> get(String path, String token) throws Exception {
        HttpRequest.Builder request = HttpRequest.newBuilder(URI.create("http://127.0.0.1:" + port + path));
        if (token != null) {
            request.header("Authorization", "Bearer " + token);
        }
        return HttpClient.newHttpClient().send(request.build(), HttpResponse.BodyHandlers.ofString());
    }

    private static HttpServer stub() {
        try {
            HttpServer server = HttpServer.create(new InetSocketAddress("127.0.0.1", 0), 0);
            server.createContext("/", exchange -> {
                String text = exchange.getRequestMethod() + " " + exchange.getRequestURI()
                        + " authorization=" + exchange.getRequestHeaders().getFirst("Authorization");
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
