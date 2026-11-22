package com.bank.cards.card;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;

/**
 * Карта для отображения в мобильном приложении и веб-банке.
 */
@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class CardDto {

    private final Long id;
    private final Long clientId;
    private final String pan;
    private final String cardHolderName;
    private final CardType cardType;
    private final PaymentSystem paymentSystem;
    private final CardStatus status;
    private final LocalDate validFrom;
    private final LocalDate validTo;
    private final String productName;
    private final LocalDate createdDate;
}
