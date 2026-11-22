package com.bank.cards.card;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * Карта для плитки в экране-списке: без полного PAN, с вычисляемыми полями
 * и человекочитаемыми подписями.
 */
@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class CardListItemDto {

    private final Long id;
    private final Long clientId;
    private final String maskedPan;
    private final CardStatus status;
    private final String statusDisplayName;
    private final CardType cardType;
    private final PaymentSystem paymentSystem;
    private final String productName;
    private final String validFromText;
    private final boolean expired;
    private final long daysToExpiry;
}
