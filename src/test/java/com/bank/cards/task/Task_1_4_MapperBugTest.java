package com.bank.cards.task;

import com.bank.cards.card.Card;
import com.bank.cards.card.CardDto;
import com.bank.cards.card.CardMapper;
import com.bank.cards.support.BaseIntegrationTest;
import com.bank.cards.support.CardTestDataFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Задание 1.4. Охота на баг в ручном маппере.
 */
@Tag("task-1-4")
@DisplayName("Задание 1.4: сроки действия карты не перепутаны местами")
class Task_1_4_MapperBugTest extends BaseIntegrationTest {

    @Autowired
    private CardMapper cardMapper;

    @Test
    @DisplayName("validFrom в дто равен validFrom сущности")
    void validFromIsNotSwapped() {
        Card card = CardTestDataFactory.aCard()
                .validFrom(LocalDate.of(2025, 1, 1))
                .validTo(LocalDate.of(2030, 6, 30))
                .build();

        CardDto dto = cardMapper.toDto(card);

        assertThat(dto.getValidFrom()).isEqualTo(LocalDate.of(2025, 1, 1));
    }

    @Test
    @DisplayName("validTo в дто равен validTo сущности")
    void validToIsNotSwapped() {
        Card card = CardTestDataFactory.aCard()
                .validFrom(LocalDate.of(2025, 1, 1))
                .validTo(LocalDate.of(2030, 6, 30))
                .build();

        CardDto dto = cardMapper.toDto(card);

        assertThat(dto.getValidTo()).isEqualTo(LocalDate.of(2030, 6, 30));
    }

    @Test
    @DisplayName("Дата старта больше даты окончания — история из баг-репорта воспроизводится")
    void reportedBugScenario() {
        Card card = CardTestDataFactory.aCard()
                .validFrom(LocalDate.of(2024, 2, 10))
                .validTo(LocalDate.of(2029, 2, 10))
                .build();

        CardDto dto = cardMapper.toDto(card);

        assertThat(dto.getValidFrom()).isBefore(dto.getValidTo());
    }
}
