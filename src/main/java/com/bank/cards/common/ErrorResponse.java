package com.bank.cards.common;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

/**
 * Единый формат ошибок API.
 */
@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class ErrorResponse {

    private final String code;
    private final String message;
    private final LocalDateTime timestamp;
}
