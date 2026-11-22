package com.bank.cards.support;

import com.bank.cards.card.Card;
import com.bank.cards.card.CardDto;
import com.bank.cards.card.CardStatus;
import com.bank.cards.card.CardType;
import com.bank.cards.card.CreateCardRequest;
import com.bank.cards.card.PaymentSystem;
import com.bank.cards.card.UpdateCardRequest;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Тестовая фабрика сущностей, дто и запросов.
 * Изменять этот класс можно только тогда, когда этого требует сигнатура
 * продакшен-классов. Сами тесты не трогать.
 */
public final class CardTestDataFactory {

    private CardTestDataFactory() {
    }

    /** Картa по умолчанию — дебетовая «Мир», активная, срок 2025–2030. */
    public static Card.CardBuilder aCard() {
        return Card.builder()
                .clientId(1001L)
                .pan("2200000000009999")
                .cardHolderName("IVAN IVANOV")
                .cardType(CardType.DEBIT)
                .paymentSystem(PaymentSystem.MIR)
                .status(CardStatus.ACTIVE)
                .validFrom(LocalDate.of(2025, 1, 1))
                .validTo(LocalDate.of(2030, 1, 1))
                .productName("Карта Классическая")
                .createdAt(LocalDateTime.of(2025, 1, 1, 12, 0));
    }

    /** Ожидаемое дто карты 2001 из сида. */
    public static CardDto ivanovMainCardDto() {
        return new CardDto(
                2001L,
                1001L,
                "2200000000001001",
                "IVAN IVANOV",
                CardType.DEBIT,
                PaymentSystem.MIR,
                CardStatus.ACTIVE,
                LocalDate.of(2024, 3, 1),
                LocalDate.of(2029, 3, 1),
                "Карта Классическая",
                LocalDate.of(2024, 3, 1)
        );
    }

    /** Корректный запрос на выпуск новой карты. */
    public static CreateCardRequest createCardRequest() {
        return new CreateCardRequest(
                1001L,
                "2200000000007777",
                "IVAN IVANOV",
                CardType.DEBIT,
                PaymentSystem.MIR,
                LocalDate.of(2026, 1, 1),
                LocalDate.of(2031, 1, 1),
                "Карта Классическая"
        );
    }

    /** Корректный запрос на изменение карты. */
    public static UpdateCardRequest updateCardRequest() {
        return new UpdateCardRequest("Карта Цифровая Про", LocalDate.of(2031, 5, 5));
    }
}
