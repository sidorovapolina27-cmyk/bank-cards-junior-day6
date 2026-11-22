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
 * Задание 5.2. Карты, истекающие в течение N дней (JPQL).
 */
@Tag("task-5-2")
@DisplayName("Задание 5.2: GET /api/clients/{id}/cards/expiring?days=")
class Task_5_2_ExpiringCardsTest extends BaseIntegrationTest {

    @Test
    @Transactional
    @DisplayName("Клиент 1001, days=1000: активная 2001 и заблокированная 2002, по validTo asc")
    void expiringWithin1000Days() throws Exception {
        mockMvc.perform(get("/api/clients/1001/cards/expiring").param("days", "1000"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id").value(2002))
                .andExpect(jsonPath("$[1].id").value(2001));
    }

    @Test
    @Transactional
    @DisplayName("Закрытая карта (2003) не попадает в выборку, даже если срок скоро")
    void closedCardIsExcluded() throws Exception {
        mockMvc.perform(get("/api/clients/1001/cards/expiring").param("days", "5000"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[?(@.id == 2003)]").isEmpty());
    }

    @Test
    @Transactional
    @DisplayName("Клиент 1002, days=30: пустой список")
    void expiringWithin30DaysEmpty() throws Exception {
        mockMvc.perform(get("/api/clients/1002/cards/expiring").param("days", "30"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }
}
