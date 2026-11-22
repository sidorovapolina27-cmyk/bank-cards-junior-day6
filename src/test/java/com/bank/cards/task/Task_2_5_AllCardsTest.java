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
 * Задание 2.5. Полный список карт для операторов.
 */
@Tag("task-2-5")
@DisplayName("Задание 2.5: GET /api/cards — все карты, новые сверху")
class Task_2_5_AllCardsTest extends BaseIntegrationTest {

    @Test
    @Transactional
    @DisplayName("Отдаются все 8 сид-карт")
    void allSeedCardsAreServed() throws Exception {
        mockMvc.perform(get("/api/cards"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(8)));
    }

    @Test
    @Transactional
    @DisplayName("Сортировка по createdAt по убыванию: свежие сверху")
    void sortedByCreatedAtDesc() throws Exception {
        mockMvc.perform(get("/api/cards"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(2005))
                .andExpect(jsonPath("$[1].id").value(2004))
                .andExpect(jsonPath("$[2].id").value(2008))
                .andExpect(jsonPath("$[7].id").value(2007));
    }
}
