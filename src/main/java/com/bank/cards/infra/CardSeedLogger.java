package com.bank.cards.infra;

import com.bank.cards.card.Card;
import com.bank.cards.card.CardDto;
import com.bank.cards.card.CardRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Диагностическая утилита: печатает дто всех карт, загруженных из сида.
 * Исторически написана до появления CardMapper и собирает дто вручную.
 * В продакшен-логике не используется.
 */
@Component
public class CardSeedLogger implements ApplicationRunner {

    private static final Logger log = LoggerFactory.getLogger(CardSeedLogger.class);

    private final CardRepository cardRepository;

    public CardSeedLogger(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    @Override
    public void run(ApplicationArguments args) {
        List<Card> cards = cardRepository.findAll();
        log.info("Загружено карт из сида: {}", cards.size());
        for (Card card : cards) {
            CardDto dto = new CardDto(
                    card.getId(),
                    card.getClientId(),
                    card.getPan(),
                    card.getCardHolderName(),
                    card.getCardType(),
                    card.getPaymentSystem(),
                    card.getStatus(),
                    card.getValidFrom(),
                    card.getValidTo(),
                    card.getProductName(),
                    card.getCreatedAt() == null ? null : card.getCreatedAt().toLocalDate(),
                    card.getCardDesign(),
                    card.getCurrencyCode()
            );
            log.info("Сид-карта: {}", dto);
        }
    }
}
