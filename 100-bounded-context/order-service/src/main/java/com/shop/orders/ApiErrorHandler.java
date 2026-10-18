package com.shop.orders;

import java.net.URI;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiErrorHandler {
    @ExceptionHandler(UnknownProductException.class)
    public ProblemDetail unknownProduct(UnknownProductException exception) {
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND,
                exception.getMessage());
        problem.setType(URI.create("https://api.shop.example/problems/product-not-found"));
        return problem;
    }
}
