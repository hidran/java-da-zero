package com.shop.orders;

import java.net.URI;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.ErrorResponseException;

public class OrderNotFoundException extends ErrorResponseException {
    public OrderNotFoundException(long id) {
        super(HttpStatus.NOT_FOUND, problem(id), null);
    }

    private static ProblemDetail problem(long id) {
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, "Order " + id + " does not exist");
        problem.setType(URI.create("https://api.shop.example/problems/order-not-found"));
        problem.setTitle("Order not found");
        return problem;
    }
}
