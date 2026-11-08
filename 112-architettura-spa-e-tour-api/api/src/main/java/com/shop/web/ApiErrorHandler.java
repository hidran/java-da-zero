package com.shop.web;

import com.shop.catalog.CategoryNotFoundException;
import com.shop.catalog.DuplicateProductNameException;
import com.shop.catalog.ProductNotFoundException;
import java.net.URI;
import java.util.Map;
import java.util.TreeMap;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class ApiErrorHandler extends ResponseEntityExceptionHandler {
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException exception,
            HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        Map<String, String> errors = new TreeMap<>();
        exception.getBindingResult().getFieldErrors()
                .forEach(error -> errors.putIfAbsent(error.getField(), error.getDefaultMessage()));
        return handleExceptionInternal(exception, validationProblem(errors), headers, status, request);
    }

    @ExceptionHandler(DuplicateProductNameException.class)
    public ProblemDetail duplicateName(DuplicateProductNameException exception) {
        return validationProblem(Map.of("name", exception.getMessage()));
    }

    @ExceptionHandler(CategoryNotFoundException.class)
    public ProblemDetail unknownCategory(CategoryNotFoundException exception) {
        return validationProblem(Map.of("categoryId", exception.getMessage()));
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ProblemDetail productNotFound(ProductNotFoundException exception) {
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, exception.getMessage());
        problem.setType(URI.create("https://api.shop.example/problems/product-not-found"));
        problem.setTitle("Product not found");
        return problem;
    }

    // One shape for every field error, so the web client reads them all the same way.
    private static ProblemDetail validationProblem(Map<String, String> errors) {
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(
                HttpStatus.BAD_REQUEST, "One or more fields are invalid.");
        problem.setType(URI.create("https://api.shop.example/problems/validation-error"));
        problem.setTitle("Request validation failed");
        problem.setProperty("errors", errors);
        return problem;
    }
}
