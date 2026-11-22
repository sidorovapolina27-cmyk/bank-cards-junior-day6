package com.bank.cards.task;

import com.bank.cards.support.BaseIntegrationTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Задание 5.1. История операций по карте.
 */
@Tag("task-5-1")
@DisplayName("Задание 5.1: GET /api/cards/{id}/transactions")
class Task_5_1_CardTransactionsTest extends BaseIntegrationTest {

    @Test
    @Transactional
    @DisplayName("Карта 2001: 8 операций, свежие сверху")
    void card2001TransactionsOrderedDesc() throws Exception {
        mockMvc.perform(get("/api/cards/2001/transactions"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(8)))
                .andExpect(jsonPath("$[0].id").value(3008))
                .andExpect(jsonPath("$[7].id").value(3001));
    }

    @Test
    @Transactional
    @DisplayName("Поля операции заполнены полностью")
    void transactionFieldsAreFilled() throws Exception {
        mockMvc.perform(get("/api/cards/2001/transactions"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].cardId").value(2001))
                .andExpect(jsonPath("$[0].type").value("DEBIT"))
                .andExpect(jsonPath("$[0].description").value("Покупка: Электроника"))
                .andExpect(jsonPath("$[0].currencyCode").value("RUB"))
                .andExpect(jsonPath("$[0].occurredAt").value("2026-02-14T20:10:00"));
    }

    @Test
    @Transactional
    @DisplayName("Карта 2002: 3 операции; несуществующая карта — 404")
    void card2002AndUnknownCard() throws Exception {
        mockMvc.perform(get("/api/cards/2002/transactions"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].id").value(3011));

        mockMvc.perform(get("/api/cards/999999/transactions"))
                .andExpect(status().isNotFound());
    }
}
