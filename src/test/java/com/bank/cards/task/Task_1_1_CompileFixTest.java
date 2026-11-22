package com.bank.cards.task;

import com.bank.cards.support.BaseIntegrationTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Задание 1.1. Починка компиляции после чужого изменения CardDto.
 */
@Tag("task-1-1")
@DisplayName("Задание 1.1: проект компилируется, cardHolderName доезжает до клиента")
class Task_1_1_CompileFixTest extends BaseIntegrationTest {

    @Test
    @DisplayName("GET /api/cards/2001 отдаёт имя держателя карты")
    void cardJsonContainsHolderName() throws Exception {
        mockMvc.perform(get("/api/cards/2001"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cardHolderName").value("IVAN IVANOV"));
    }
}
