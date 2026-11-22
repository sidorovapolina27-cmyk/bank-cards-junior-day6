package com.bank.cards.card;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

/**
 * Запрос на выпуск новой карты.
 * Статус и дата создания не передаются: новая карта всегда NEW,
 * createdAt проставляет сервер.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateCardRequest {

    private Long clientId;
    private String pan;
    private String cardHolderName;
    private CardType cardType;
    private PaymentSystem paymentSystem;
    private LocalDate validFrom;
    private LocalDate validTo;
    private String productName;
}
