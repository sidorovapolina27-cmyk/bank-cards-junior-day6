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
 * Задание 3.2. Детальный экран карты: вложенный дто клиента.
 */
@Tag("task-3-2")
@DisplayName("Задание 3.2: GET /api/cards/{id}/details — карта + клиент")
class Task_3_2_CardDetailsTest extends BaseIntegrationTest {

    @Test
    @Transactional
    @DisplayName("GET /api/cards/2001/details — карта и вложенный клиент Иванов")
    void card2001DetailsWithClient() throws Exception {
        mockMvc.perform(get("/api/cards/2001/details"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(2001))
                .andExpect(jsonPath("$.pan").value("2200000000001001"))
                .andExpect(jsonPath("$.status").value("ACTIVE"))
                .andExpect(jsonPath("$.productName").value("Карта Классическая"))
                .andExpect(jsonPath("$.client.id").value(1001))
                .andExpect(jsonPath("$.client.fullName").value("Иванов Иван Иванович"))
                .andExpect(jsonPath("$.client.phoneNumber").value("+7 913 123-45-67"))
                .andExpect(jsonPath("$.client.email").value("ivanov@mail.ru"));
    }

    @Test
    @Transactional
    @DisplayName("GET /api/cards/2004/details — клиент Петрова")
    void card2004DetailsWithClient() throws Exception {
        mockMvc.perform(get("/api/cards/2004/details"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.client.fullName").value("Петрова Мария Сергеевна"))
                .andExpect(jsonPath("$.cardHolderName").value("MARIA PETROVA"));
    }

    @Test
    @Transactional
    @DisplayName("GET /api/cards/999999/details — 404")
    void unknownCardIs404() throws Exception {
        mockMvc.perform(get("/api/cards/999999/details"))
                .andExpect(status().isNotFound());
    }
}
