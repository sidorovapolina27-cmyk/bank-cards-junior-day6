package com.bank.cards.card;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

/**
 * Отчёт по картам клиента — для экрана «Мои карты».
 */
@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class ClientCardsReportDto {

    private final Long clientId;
    private final String clientName;
    private final List<CardListItemDto> cards;
    private final int totalActive;
}
