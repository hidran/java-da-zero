package com.shop.gateway;

import java.util.UUID;
import org.springframework.web.servlet.function.HandlerFilterFunction;
import org.springframework.web.servlet.function.ServerRequest;
import org.springframework.web.servlet.function.ServerResponse;

public final class RequestIdFilter {
    public static final String HEADER = "X-Request-Id";

    private RequestIdFilter() {
    }
    private static String idFrom(ServerRequest request) {
        String incoming = request.headers().firstHeader(HEADER);
        return incoming == null || incoming.isBlank() ? UUID.randomUUID().toString() : incoming;
    }
    private static ServerRequest withId(ServerRequest request, String requestId) {
        return ServerRequest.from(request)
                .headers(headers -> headers.set(HEADER, requestId)).build();
    }
    public static HandlerFilterFunction<ServerResponse, ServerResponse> requestId() {
        return (request, next) -> {
            String requestId = idFrom(request);
            ServerResponse response = next.handle(withId(request, requestId));
            response.headers().set(HEADER, requestId);
            return response;
        };
    }
}
