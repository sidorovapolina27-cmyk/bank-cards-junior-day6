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
 * Задание 3.5. Отчёт по картам клиента.
 */
@Tag("task-3-5")
@DisplayName("Задание 3.5: GET /api/clients/{id}/cards-report")
class Task_3_5_CardsReportTest extends BaseIntegrationTest {

    @Test
    @Transactional
    @DisplayName("Отчёт по клиенту 1001: ФИО, 3 карты, одна активная")
    void reportForClient1001() throws Exception {
        mockMvc.perform(get("/api/clients/1001/cards-report"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.clientId").value(1001))
                .andExpect(jsonPath("$.clientName").value("Иванов Иван Иванович"))
                .andExpect(jsonPath("$.totalActive").value(1))
                .andExpect(jsonPath("$.cards", hasSize(3)))
                .andExpect(jsonPath("$.cards[0].maskedPan").value("**** 1001"))
                .andExpect(jsonPath("$.cards[0].statusDisplayName").value("Активна"));
    }

    @Test
    @Transactional
    @DisplayName("Отчёт по клиенту 1003: две активные из трёх")
    void reportForClient1003() throws Exception {
        mockMvc.perform(get("/api/clients/1003/cards-report"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.clientName").value("Сидоров Алексей Петрович"))
                .andExpect(jsonPath("$.totalActive").value(2))
                .andExpect(jsonPath("$.cards", hasSize(3)));
    }

    @Test
    @Transactional
    @DisplayName("Отчёт по неизвестному клиенту — 404")
    void reportForUnknownClientIs404() throws Exception {
        mockMvc.perform(get("/api/clients/9999/cards-report"))
                .andExpect(status().isNotFound());
    }
}
