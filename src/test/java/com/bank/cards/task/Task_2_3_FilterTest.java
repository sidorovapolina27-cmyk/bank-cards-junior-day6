package com.bank.cards.task;

import com.bank.cards.card.CardDto;
import com.bank.cards.card.CardService;
import com.bank.cards.card.CardStatus;
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
 * Задание 2.3. Фильтр карт клиента по статусу.
 */
@Tag("task-2-3")
@DisplayName("Задание 2.3: GET /api/clients/{id}/cards?status=")
class Task_2_3_FilterTest extends BaseIntegrationTest {

    @Autowired
    private CardService cardService;

    @Test
    @Transactional
    @DisplayName("Без параметра — все карты клиента по возрастанию id (старое поведение)")
    void withoutParamReturnsAllCards() throws Exception {
        mockMvc.perform(get("/api/clients/1001/cards"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].id").value(2001))
                .andExpect(jsonPath("$[1].id").value(2002))
                .andExpect(jsonPath("$[2].id").value(2003));
    }

    @Test
    @Transactional
    @DisplayName("status=ACTIVE — только активные карты")
    void filterByActive() throws Exception {
        mockMvc.perform(get("/api/clients/1001/cards").param("status", "ACTIVE"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id").value(2001));
    }

    @Test
    @Transactional
    @DisplayName("status=BLOCKED — только заблокированные карты")
    void filterByBlocked() throws Exception {
        mockMvc.perform(get("/api/clients/1001/cards").param("status", "BLOCKED"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id").value(2002));
    }

    @Test
    @Transactional
    @DisplayName("status=EXPIRED у клиента без таких карт — пустой массив")
    void filterEmptyResult() throws Exception {
        mockMvc.perform(get("/api/clients/1001/cards").param("status", "EXPIRED"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    @Transactional
    @DisplayName("Неизвестный статус отвечает 400")
    void unknownStatusIs400() throws Exception {
        mockMvc.perform(get("/api/clients/1001/cards").param("status", "BROKEN"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Transactional
    @DisplayName("Сервисный метод getCardsByClient(clientId, status) фильтрует")
    void serviceMethodFilters() {
        List<CardDto> active = cardService.getCardsByClient(1003L, CardStatus.ACTIVE);
        List<CardDto> all = cardService.getCardsByClient(1003L, null);

        assertThat(active).extracting(CardDto::getId).containsExactly(2006L, 2008L);
        assertThat(all).extracting(CardDto::getId).containsExactly(2006L, 2007L, 2008L);
    }
}
