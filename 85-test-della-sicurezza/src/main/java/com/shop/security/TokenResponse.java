package com.shop.security;

public record TokenResponse(String token, long expiresInSeconds) {
}
