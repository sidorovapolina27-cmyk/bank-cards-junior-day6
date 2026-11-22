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
 * Задание 5.4. Поиск операций за период: порядок параметров важен.
 */
@Tag("task-5-4")
@DisplayName("Задание 5.4: GET /api/cards/{id}/transactions/search?from=&to=")
class Task_5_4_TransactionsSearchTest extends BaseIntegrationTest {

    @Test
    @Transactional
    @DisplayName("Январь 2026 по карте 2001: три операции, свежие сверху")
    void januaryPeriod() throws Exception {
        mockMvc.perform(get("/api/cards/2001/transactions/search")
                        .param("from", "2026-01-01T00:00:00")
                        .param("to", "2026-01-31T23:59:59"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].id").value(3006))
                .andExpect(jsonPath("$[1].id").value(3005))
                .andExpect(jsonPath("$[2].id").value(3004));
    }

    @Test
    @Transactional
    @DisplayName("Декабрь 2025 по карте 2001: три операции")
    void decemberPeriod() throws Exception {
        mockMvc.perform(get("/api/cards/2001/transactions/search")
                        .param("from", "2025-12-01T00:00:00")
                        .param("to", "2025-12-31T23:59:59"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].id").value(3003));
    }

    @Test
    @Transactional
    @DisplayName("Период без операций — пустой массив")
    void emptyPeriod() throws Exception {
        mockMvc.perform(get("/api/cards/2001/transactions/search")
                        .param("from", "2024-01-01T00:00:00")
                        .param("to", "2024-12-31T23:59:59"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    @Transactional
    @DisplayName("Перепутанные местами from и to дают пустой результат")
    void swappedBoundsGiveEmpty() throws Exception {
        mockMvc.perform(get("/api/cards/2001/transactions/search")
                        .param("from", "2026-01-31T23:59:59")
                        .param("to", "2026-01-01T00:00:00"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }
}
