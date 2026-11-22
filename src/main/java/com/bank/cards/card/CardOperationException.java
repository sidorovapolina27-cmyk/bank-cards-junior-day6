package com.bank.cards.card;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Нарушение бизнес-правила при работе с картой.
 */
@ResponseStatus(HttpStatus.CONFLICT)
public class CardOperationException extends RuntimeException {

    public CardOperationException(String message) {
        super(message);
    }
}
