package com.bank.cards.common;

import com.bank.cards.card.CardNotFoundException;
import com.bank.cards.card.CardOperationException;
import com.bank.cards.client.ClientNotFoundException;
import com.bank.cards.limit.LimitAlreadyExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

/**
 * Единая обработка ошибок API: все ошибки отдаются в формате ErrorResponse.
 */
@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(CardNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleCardNotFound(CardNotFoundException e) {
        return build(HttpStatus.NOT_FOUND, "CARD_NOT_FOUND", e.getMessage());
    }

    @ExceptionHandler(ClientNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleClientNotFound(ClientNotFoundException e) {
        return build(HttpStatus.NOT_FOUND, "CLIENT_NOT_FOUND", e.getMessage());
    }

    @ExceptionHandler(CardOperationException.class)
    public ResponseEntity<ErrorResponse> handleCardOperation(CardOperationException e) {
        return build(HttpStatus.CONFLICT, "CARD_OPERATION_CONFLICT", e.getMessage());
    }

    @ExceptionHandler(LimitAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleLimitAlreadyExists(LimitAlreadyExistsException e) {
        return build(HttpStatus.CONFLICT, "LIMIT_ALREADY_EXISTS", e.getMessage());
    }

    private ResponseEntity<ErrorResponse> build(HttpStatus status, String code, String message) {
        return ResponseEntity.status(status)
                .body(new ErrorResponse(code, message, LocalDateTime.now()));
    }
}
