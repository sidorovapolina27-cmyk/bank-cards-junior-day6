package com.bank.cards.task;

import com.bank.cards.card.CardDto;
import com.bank.cards.card.CardService;
import com.bank.cards.support.BaseIntegrationTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Задание 1.3. Список карт клиента.
 */
@Tag("task-1-3")
@DisplayName("Задание 1.3: список карт клиента")
class Task_1_3_ClientCardsTest extends BaseIntegrationTest {

    @Autowired
    private CardService cardService;

    @Test
    @Transactional
    @DisplayName("GET /api/clients/1001/cards — три карты, по возрастанию id")
    void client1001HasThreeCards() throws Exception {
        mockMvc.perform(get("/api/clients/1001/cards"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].id").value(2001))
                .andExpect(jsonPath("$[0].status").value("ACTIVE"))
                .andExpect(jsonPath("$[1].id").value(2002))
                .andExpect(jsonPath("$[1].status").value("BLOCKED"))
                .andExpect(jsonPath("$[2].id").value(2003))
                .andExpect(jsonPath("$[2].status").value("CLOSED"));
    }

    @Test
    @Transactional
    @DisplayName("GET /api/clients/1003/cards — три карты, по возрастанию id")
    void client1003HasThreeCards() throws Exception {
        mockMvc.perform(get("/api/clients/1003/cards"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].id").value(2006))
                .andExpect(jsonPath("$[1].id").value(2007))
                .andExpect(jsonPath("$[2].id").value(2008));
    }

    @Test
    @Transactional
    @DisplayName("Клиент без карт получает пустой массив, а не ошибку")
    void clientWithoutCardsGetsEmptyArray() throws Exception {
        mockMvc.perform(get("/api/clients/9999/cards"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    @Transactional
    @DisplayName("CardService.getCardsByClient(1002) — карты 2004 и 2005")
    void serviceReturnsClient1002Cards() {
        List<CardDto> cards = cardService.getCardsByClient(1002L);

        assertThat(cards).extracting(CardDto::getId).containsExactly(2004L, 2005L);
    }
}
