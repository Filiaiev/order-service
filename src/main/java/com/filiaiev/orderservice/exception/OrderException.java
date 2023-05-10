package com.filiaiev.orderservice.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class OrderException extends RuntimeException {

    private final HttpStatus statusCode;

    public OrderException(String message, HttpStatus statusCode) {
        super(message);
        this.statusCode = statusCode;
    }

    public OrderException(String message) {
        this(message, HttpStatus.BAD_REQUEST);
    }
}
