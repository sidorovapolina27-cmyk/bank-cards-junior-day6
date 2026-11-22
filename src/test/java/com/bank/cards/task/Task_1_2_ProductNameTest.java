package com.bank.cards.task;

import com.bank.cards.support.BaseIntegrationTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Задание 1.2. Поле productName в CardDto.
 */
@Tag("task-1-2")
@DisplayName("Задание 1.2: productName в ответе API")
class Task_1_2_ProductNameTest extends BaseIntegrationTest {

    @Test
    @DisplayName("GET /api/cards/2001 отдаёт название продукта «Карта Классическая»")
    void debitCardProductName() throws Exception {
        mockMvc.perform(get("/api/cards/2001"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productName").value("Карта Классическая"));
    }

    @Test
    @DisplayName("GET /api/cards/2002 отдаёт название продукта «Карта Кредитная»")
    void creditCardProductName() throws Exception {
        mockMvc.perform(get("/api/cards/2002"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productName").value("Карта Кредитная"));
    }
}
