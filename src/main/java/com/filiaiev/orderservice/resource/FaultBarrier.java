package com.filiaiev.orderservice.resource;

import com.filiaiev.orderservice.exception.ErrorDetails;
import com.filiaiev.orderservice.exception.OrderException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
@Slf4j
public class FaultBarrier {

    @ExceptionHandler(OrderException.class)
    public ResponseEntity<ErrorDetails> handleOrderCreationException(OrderException ex, WebRequest request) {
        return prepareErrorResponse(ex, ex.getMessage(), request, ex.getStatusCode());
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorDetails> handleAccessDeniedException(AccessDeniedException ex, WebRequest request) {
        return prepareErrorResponse(ex, ex.getMessage(), request, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetails> handleException(Exception ex, WebRequest request) {
        return prepareErrorResponse(ex, ex.getMessage(), request, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<ErrorDetails> prepareErrorResponse(Exception ex, String message, WebRequest request, HttpStatus httpStatus) {
        log.error(ex.getMessage(), ex);

        return new ResponseEntity<>(new ErrorDetails(message, request.getDescription(false)), httpStatus);
    }
}
