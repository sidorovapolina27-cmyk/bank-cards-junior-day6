package com.bank.cards.limit;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class LimitDto {

    private final Long id;
    private final Long cardId;
    private final LimitType limitType;
    private final BigDecimal amount;
}
