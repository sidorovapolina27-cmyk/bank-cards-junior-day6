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
 * Задание 6.1. Новая колонка card_design: миграция + вся вертикаль.
 */
@Tag("task-6-1")
@DisplayName("Задание 6.1: card_design доезжает из базы до API")
class Task_6_1_CardDesignTest extends BaseIntegrationTest {

    @Test
    @Transactional
    @DisplayName("Карта 2001 отдаёт дизайн «Синяя классика»")
    void card2001Design() throws Exception {
        mockMvc.perform(get("/api/cards/2001"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cardDesign").value("Синяя классика"));
    }

    @Test
    @Transactional
    @DisplayName("Карта 2005 отдаёт дизайн «Премиум тёмная»")
    void card2005Design() throws Exception {
        mockMvc.perform(get("/api/cards/2005"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cardDesign").value("Премиум тёмная"));
    }

    @Test
    @Transactional
    @DisplayName("Карта 2008 отдаёт дизайн «Неон цифровой»")
    void card2008Design() throws Exception {
        mockMvc.perform(get("/api/cards/2008"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cardDesign").value("Неон цифровой"));
    }
}
