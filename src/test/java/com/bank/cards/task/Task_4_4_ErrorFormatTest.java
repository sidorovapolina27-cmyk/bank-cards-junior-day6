package com.bank.cards.task;

import com.bank.cards.support.BaseIntegrationTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Задание 4.4. Единый формат ошибок API.
 */
@Tag("task-4-4")
@DisplayName("Задание 4.4: ErrorResponse для всех ошибок API")
class Task_4_4_ErrorFormatTest extends BaseIntegrationTest {

    @Test
    @Transactional
    @DisplayName("404: код CARD_NOT_FOUND, сообщение и timestamp")
    void cardNotFoundBody() throws Exception {
        mockMvc.perform(get("/api/cards/999999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value("CARD_NOT_FOUND"))
                .andExpect(jsonPath("$.message").value("Карта 999999 не найдена"))
                .andExpect(jsonPath("$.timestamp").isNotEmpty());
    }

    @Test
    @Transactional
    @DisplayName("404: код CLIENT_NOT_FOUND для отчёта по неизвестному клиенту")
    void clientNotFoundBody() throws Exception {
        mockMvc.perform(get("/api/clients/9999/cards-report"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value("CLIENT_NOT_FOUND"))
                .andExpect(jsonPath("$.message", containsString("9999")))
                .andExpect(jsonPath("$.timestamp").isNotEmpty());
    }

    @Test
    @Transactional
    @DisplayName("409: код CARD_OPERATION_CONFLICT при повторном закрытии карты")
    void conflictBody() throws Exception {
        mockMvc.perform(delete("/api/cards/2003"))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.code").value("CARD_OPERATION_CONFLICT"))
                .andExpect(jsonPath("$.message").value("Карта 2003 уже закрыта"))
                .andExpect(jsonPath("$.timestamp").isNotEmpty());
    }

    @Test
    @Transactional
    @DisplayName("Некорректный параметр статуса продолжает отвечать 400")
    void badStatusParamStill400() throws Exception {
        mockMvc.perform(get("/api/clients/1001/cards").param("status", "BROKEN"))
                .andExpect(status().isBadRequest());
    }
}
