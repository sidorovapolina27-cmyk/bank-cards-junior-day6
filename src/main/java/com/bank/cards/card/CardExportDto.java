package com.bank.cards.card;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;

/**
 * Экспортный дто карты для партнёрского канала.
 *
 * Контракт JSON:
 * - ключи в snake_case;
 * - даты в формате dd.MM.yyyy;
 * - внутренний риск-скор наружу не отдаётся.
 */
@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class CardExportDto {

    @JsonProperty("pan")
    private final String pan;

    @JsonProperty("card_holder_name")
    private final String cardHolderName;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy")
    @JsonProperty("valid_from")
    private final LocalDate validFrom;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy")
    @JsonProperty("valid_to")
    private final LocalDate validTo;

    @JsonProperty("status")
    private final CardStatus status;

    @JsonProperty("product_name")
    private final String productName;

    @JsonIgnore
    private final int internalRiskScore;
}
