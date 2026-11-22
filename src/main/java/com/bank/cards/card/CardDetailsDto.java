package com.bank.cards.card;

import com.bank.cards.client.ClientDto;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;

/**
 * Карта с данными клиента — для детального экрана карты.
 */
@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class CardDetailsDto {

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
    private final ClientDto client;
}
