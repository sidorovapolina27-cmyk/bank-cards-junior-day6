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
 * Задание 5.3. Пагинация истории операций.
 */
@Tag("task-5-3")
@DisplayName("Задание 5.3: GET /api/cards/{id}/transactions/page")
class Task_5_3_TransactionsPageTest extends BaseIntegrationTest {

    @Test
    @Transactional
    @DisplayName("Первая страница: 3 записи из 8, всего 3 страницы, свежие сверху")
    void firstPage() throws Exception {
        mockMvc.perform(get("/api/cards/2001/transactions/page")
                        .param("page", "0").param("size", "3"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(3)))
                .andExpect(jsonPath("$.content[0].id").value(3008))
                .andExpect(jsonPath("$.totalElements").value(8))
                .andExpect(jsonPath("$.totalPages").value(3))
                .andExpect(jsonPath("$.number").value(0));
    }

    @Test
    @Transactional
    @DisplayName("Последняя страница: 2 записи")
    void lastPage() throws Exception {
        mockMvc.perform(get("/api/cards/2001/transactions/page")
                        .param("page", "2").param("size", "3"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(2)))
                .andExpect(jsonPath("$.content[0].id").value(3002))
                .andExpect(jsonPath("$.content[1].id").value(3001))
                .andExpect(jsonPath("$.number").value(2));
    }

    @Test
    @Transactional
    @DisplayName("Несуществующая карта — 404")
    void unknownCardIs404() throws Exception {
        mockMvc.perform(get("/api/cards/999999/transactions/page")
                        .param("page", "0").param("size", "3"))
                .andExpect(status().isNotFound());
    }
}
