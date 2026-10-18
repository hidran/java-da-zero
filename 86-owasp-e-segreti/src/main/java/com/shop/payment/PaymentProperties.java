package com.shop.payment;

import jakarta.validation.constraints.NotBlank;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties("shop.payment")
public record PaymentProperties(@NotBlank String apiKey) {
    @Override
    public String toString() {
        return "PaymentProperties[apiKey=****]";
    }
}
