package com.bank.cards.task;

import com.bank.cards.support.BaseIntegrationTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Задание 6.2. Лимиты по картам: таблица + API.
 */
@Tag("task-6-2")
@DisplayName("Задание 6.2: лимиты карты — POST/GET /api/cards/{id}/limits")
class Task_6_2_CardLimitsTest extends BaseIntegrationTest {

    @Test
    @Transactional
    @DisplayName("POST создаёт месячный лимит и отвечает 201")
    void createMonthlyLimit() throws Exception {
        String body = """
                {"limitType":"MONTHLY","amount":500000.00}
                """;

        mockMvc.perform(post("/api/cards/2001/limits")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.cardId").value(2001))
                .andExpect(jsonPath("$.limitType").value("MONTHLY"))
                .andExpect(jsonPath("$.amount").value(500000.00));
    }

    @Test
    @Transactional
    @DisplayName("Созданный лимит виден в GET /api/cards/{id}/limits")
    void createdLimitIsListed() throws Exception {
        String body = """
                {"limitType":"MONTHLY","amount":250000.00}
                """;

        mockMvc.perform(post("/api/cards/2004/limits")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isCreated());

        mockMvc.perform(get("/api/cards/2004/limits"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[?(@.limitType == 'MONTHLY')]", hasSize(1)));
    }

    @Test
    @Transactional
    @DisplayName("Повторный лимит того же типа отвечает 409 и кодом LIMIT_ALREADY_EXISTS")
    void duplicateLimitIs409() throws Exception {
        String body = """
                {"limitType":"MONTHLY","amount":10000.00}
                """;

        mockMvc.perform(post("/api/cards/2002/limits")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isCreated());

        mockMvc.perform(post("/api/cards/2002/limits")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.code").value("LIMIT_ALREADY_EXISTS"))
                .andExpect(jsonPath("$.message", org.hamcrest.Matchers.containsString("MONTHLY")));
    }

    @Test
    @Transactional
    @DisplayName("Лимит для несуществующей карты — 404")
    void limitForUnknownCardIs404() throws Exception {
        String body = """
                {"limitType":"DAILY","amount":10000.00}
                """;

        mockMvc.perform(post("/api/cards/999999/limits")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value("CARD_NOT_FOUND"));
    }
}
