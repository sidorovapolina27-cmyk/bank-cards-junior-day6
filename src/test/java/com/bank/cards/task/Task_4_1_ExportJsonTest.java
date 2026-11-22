package com.bank.cards.task;

import com.bank.cards.support.BaseIntegrationTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Задание 4.1. Экспортный JSON-контракт карты.
 */
@Tag("task-4-1")
@DisplayName("Задание 4.1: GET /api/cards/{id}/export — партнёрский JSON")
class Task_4_1_ExportJsonTest extends BaseIntegrationTest {

    @Test
    @Transactional
    @DisplayName("Ключи в snake_case, даты dd.MM.yyyy")
    void exportUsesSnakeCaseAndDatePattern() throws Exception {
        mockMvc.perform(get("/api/cards/2001/export"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.pan").value("2200000000001001"))
                .andExpect(jsonPath("$.card_holder_name").value("IVAN IVANOV"))
                .andExpect(jsonPath("$.valid_from").value("01.03.2024"))
                .andExpect(jsonPath("$.valid_to").value("01.03.2029"))
                .andExpect(jsonPath("$.status").value("ACTIVE"))
                .andExpect(jsonPath("$.product_name").value("Карта Классическая"));
    }

    @Test
    @Transactional
    @DisplayName("Внутренний риск-скор не сериализуется ни в каком виде")
    void internalRiskScoreIsHidden() throws Exception {
        mockMvc.perform(get("/api/cards/2001/export"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.internalRiskScore").doesNotExist())
                .andExpect(jsonPath("$.internal_risk_score").doesNotExist());
    }

    @Test
    @Transactional
    @DisplayName("Экспорт несуществующей карты — 404")
    void exportUnknownCardIs404() throws Exception {
        mockMvc.perform(get("/api/cards/999999/export"))
                .andExpect(status().isNotFound());
    }
}
