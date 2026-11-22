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
 * Задание 6.4. Дефолтные дневные лимиты: data-migration.
 */
@Tag("task-6-4")
@DisplayName("Задание 6.4: миграция проставляет DAILY 100000 всем картам")
class Task_6_4_DefaultLimitsTest extends BaseIntegrationTest {

    @Test
    @Transactional
    @DisplayName("Карта 2008 получила DAILY лимит 100000")
    void card2008HasDefaultLimit() throws Exception {
        mockMvc.perform(get("/api/cards/2008/limits"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[?(@.limitType == 'DAILY' && @.amount == 100000.0)]", hasSize(1)));
    }

    @Test
    @Transactional
    @DisplayName("Закрытая карта 2003 тоже получила лимит")
    void closedCard2003HasDefaultLimit() throws Exception {
        mockMvc.perform(get("/api/cards/2003/limits"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[?(@.limitType == 'DAILY' && @.amount == 100000.0)]", hasSize(1)));
    }

    @Test
    @Transactional
    @DisplayName("Карта 2006 — лимит в её валюте не путается: сумма 100000")
    void card2006HasDefaultLimit() throws Exception {
        mockMvc.perform(get("/api/cards/2006/limits"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[?(@.limitType == 'DAILY')]", hasSize(1)))
                .andExpect(jsonPath("$[0].amount").value(100000.00));
    }
}
