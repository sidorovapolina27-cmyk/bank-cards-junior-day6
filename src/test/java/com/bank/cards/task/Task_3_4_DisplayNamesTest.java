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
 * Задание 3.4. Человекочитаемые подписи статусов и формат дат.
 */
@Tag("task-3-4")
@DisplayName("Задание 3.4: statusDisplayName и validFromText")
class Task_3_4_DisplayNamesTest extends BaseIntegrationTest {

    @Test
    @Transactional
    @DisplayName("Активная карта: «Активна» и дата в формате dd.MM.yyyy")
    void activeCardDisplayName() throws Exception {
        mockMvc.perform(get("/api/cards/2001/summary"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusDisplayName").value("Активна"))
                .andExpect(jsonPath("$.validFromText").value("01.03.2024"));
    }

    @Test
    @Transactional
    @DisplayName("Заблокированная карта: «Заблокирована»")
    void blockedCardDisplayName() throws Exception {
        mockMvc.perform(get("/api/cards/2002/summary"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusDisplayName").value("Заблокирована"));
    }

    @Test
    @Transactional
    @DisplayName("Новая карта: «Новая»")
    void newCardDisplayName() throws Exception {
        mockMvc.perform(get("/api/cards/2005/summary"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusDisplayName").value("Новая"));
    }

    @Test
    @Transactional
    @DisplayName("Истёкшая карта: «Истекла»")
    void expiredCardDisplayName() throws Exception {
        mockMvc.perform(get("/api/cards/2007/summary"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusDisplayName").value("Истекла"));
    }

    @Test
    @Transactional
    @DisplayName("Закрытая карта: «Закрыта»")
    void closedCardDisplayName() throws Exception {
        mockMvc.perform(get("/api/cards/2003/summary"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusDisplayName").value("Закрыта"));
    }
}
