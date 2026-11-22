package com.bank.cards.transaction;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Операция по карте для отображения.
 */
@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class TransactionDto {

    private final Long id;
    private final Long cardId;
    private final BigDecimal amount;
    private final String currencyCode;
    private final TxType type;
    private final String description;
    private final LocalDateTime occurredAt;
}
