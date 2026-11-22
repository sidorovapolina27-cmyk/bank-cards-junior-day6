package com.bank.cards.card;

import com.bank.cards.support.BaseIntegrationTest;
import com.bank.cards.support.CardTestDataFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Проверки CardService.getCard.
 */
@DisplayName("CardService.getCard")
class CardServiceGetByIdTest extends BaseIntegrationTest {

    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private CardService cardService;

    @Test
    @Transactional
    @DisplayName("Карта 2001 из сида отдаётся с корректными базовыми полями")
    void seededCardMappedToDto() {
        CardDto dto = cardService.getCard(2001L);

        assertThat(dto.getId()).isEqualTo(2001L);
        assertThat(dto.getClientId()).isEqualTo(1001L);
        assertThat(dto.getPan()).isEqualTo("2200000000001001");
        assertThat(dto.getCardHolderName()).isEqualTo("IVAN IVANOV");
        assertThat(dto.getCardType()).isEqualTo(CardType.DEBIT);
        assertThat(dto.getPaymentSystem()).isEqualTo(PaymentSystem.MIR);
        assertThat(dto.getStatus()).isEqualTo(CardStatus.ACTIVE);
    }

    @Test
    @Transactional
    @DisplayName("Свежесохранённая карта корректно маппится в дто")
    void freshlySavedCardMappedToDto() {
        Card card = CardTestDataFactory.aCard().build();
        cardRepository.saveAndFlush(card);

        CardDto dto = cardService.getCard(card.getId());

        assertThat(dto.getId()).isEqualTo(card.getId());
        assertThat(dto.getPan()).isEqualTo("2200000000009999");
        assertThat(dto.getCardHolderName()).isEqualTo("IVAN IVANOV");
        assertThat(dto.getCardType()).isEqualTo(CardType.DEBIT);
        assertThat(dto.getStatus()).isEqualTo(CardStatus.ACTIVE);
    }
}
