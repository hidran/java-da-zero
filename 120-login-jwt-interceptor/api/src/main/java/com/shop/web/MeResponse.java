package com.shop.web;

import java.util.List;

public record MeResponse(String username, List<String> roles) {
}
