package com.bank.cards;

import com.bank.cards.support.BaseIntegrationTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Дымовые тесты: приложение стартует и обслуживает сид-данные.
 */
@DisplayName("Дымовая проверка приложения")
class SmokeTest extends BaseIntegrationTest {

    @Test
    @DisplayName("GET /api/cards/2001 отвечает 200 и отдаёт карту из сида")
    void seededCardIsServed() throws Exception {
        mockMvc.perform(get("/api/cards/2001"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(2001))
                .andExpect(jsonPath("$.pan").value("2200000000001001"))
                .andExpect(jsonPath("$.status").value("ACTIVE"));
    }

    @Test
    @DisplayName("GET /api/cards/999999 отвечает 404")
    void unknownCardIs404() throws Exception {
        mockMvc.perform(get("/api/cards/999999"))
                .andExpect(status().isNotFound());
    }
}
