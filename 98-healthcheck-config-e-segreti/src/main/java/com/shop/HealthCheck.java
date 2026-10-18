package com.shop;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

public final class HealthCheck {
    private HealthCheck() {
    }

    public static void main(String[] args) throws InterruptedException {
        String port = System.getenv().getOrDefault("SERVER_PORT", "8080");
        URI uri = URI.create("http://127.0.0.1:" + port + "/actuator/health/readiness");
        HttpRequest request = HttpRequest.newBuilder(uri).timeout(Duration.ofSeconds(2)).build();
        try (HttpClient client = HttpClient.newHttpClient()) {
            HttpResponse<Void> response = client.send(request, HttpResponse.BodyHandlers.discarding());
            System.exit(response.statusCode() == 200 ? 0 : 1);
        } catch (IOException exception) {
            System.exit(1);
        }
    }
}
