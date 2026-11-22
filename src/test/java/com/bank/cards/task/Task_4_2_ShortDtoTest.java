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
 * Задание 4.2. Краткий формат списка карт клиента.
 */
@Tag("task-4-2")
@DisplayName("Задание 4.2: список карт клиента в кратком формате")
class Task_4_2_ShortDtoTest extends BaseIntegrationTest {

    @Test
    @Transactional
    @DisplayName("Список клиента 1001: только короткие поля, маска вместо PAN")
    void clientCardsAreShort() throws Exception {
        mockMvc.perform(get("/api/clients/1001/cards"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].id").value(2001))
                .andExpect(jsonPath("$[0].maskedPan").value("**** 1001"))
                .andExpect(jsonPath("$[0].status").value("ACTIVE"))
                .andExpect(jsonPath("$[0].statusDisplayName").value("Активна"))
                .andExpect(jsonPath("$[0].productName").value("Карта Классическая"));
    }

    @Test
    @Transactional
    @DisplayName("Чувствительные поля в списке отсутствуют")
    void sensitiveFieldsAreGone() throws Exception {
        mockMvc.perform(get("/api/clients/1001/cards"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].pan").doesNotExist())
                .andExpect(jsonPath("$[0].cardHolderName").doesNotExist())
                .andExpect(jsonPath("$[0].validFrom").doesNotExist());
    }

    @Test
    @Transactional
    @DisplayName("Полная карта /api/cards/2001 не изменилась (guard)")
    void fullCardEndpointUnchanged() throws Exception {
        mockMvc.perform(get("/api/cards/2001"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.pan").value("2200000000001001"))
                .andExpect(jsonPath("$.cardHolderName").value("IVAN IVANOV"));
    }

    @Test
    @Transactional
    @DisplayName("Фильтр по статусу продолжает работать со кратким форматом")
    void filterStillWorks() throws Exception {
        mockMvc.perform(get("/api/clients/1001/cards").param("status", "BLOCKED"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id").value(2002))
                .andExpect(jsonPath("$[0].maskedPan").value("**** 1002"));
    }
}
