package com.bank.cards.card;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

/**
 * Запрос на изменение карты.
 * Разрешено менять только название продукта и срок действия;
 * pan, клиент и статус через этот запрос не меняются.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateCardRequest {

    private String productName;
    private LocalDate validTo;
}
