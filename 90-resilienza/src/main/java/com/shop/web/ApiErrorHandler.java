package com.shop.web;

import java.net.URI;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.RestClientException;

@RestControllerAdvice
public class ApiErrorHandler {
    @ExceptionHandler(RestClientException.class)
    public ProblemDetail supplierUnavailable(RestClientException exception) {
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(
                HttpStatus.SERVICE_UNAVAILABLE, "The supplier is not available right now.");
        problem.setType(URI.create("https://api.shop.example/problems/supplier-unavailable"));
        problem.setTitle("Supplier unavailable");
        return problem;
    }
}
