package com.bank.cards.task;

import com.bank.cards.support.BaseIntegrationTest;
import com.bank.cards.support.CardTestDataFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Задание 2.2. Изменение карты.
 */
@Tag("task-2-2")
@DisplayName("Задание 2.2: PUT /api/cards/{id} меняет только разрешённые поля")
class Task_2_2_UpdateCardTest extends BaseIntegrationTest {

    @Test
    @Transactional
    @DisplayName("PUT меняет productName и validTo, остальное не трогает")
    void updateChangesOnlyAllowedFields() throws Exception {
        String body = objectMapper.writeValueAsString(CardTestDataFactory.updateCardRequest());

        mockMvc.perform(put("/api/cards/2001")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(2001))
                .andExpect(jsonPath("$.productName").value("Карта Цифровая Про"))
                .andExpect(jsonPath("$.validTo").value("2031-05-05"))
                .andExpect(jsonPath("$.pan").value("2200000000001001"))
                .andExpect(jsonPath("$.clientId").value(1001))
                .andExpect(jsonPath("$.status").value("ACTIVE"))
                .andExpect(jsonPath("$.validFrom").value("2024-03-01"));
    }

    @Test
    @Transactional
    @DisplayName("Изменение видно при повторном чтении")
    void updateIsPersisted() throws Exception {
        String body = """
                {"productName":"Карта Пенсионная","validTo":"2032-12-31"}
                """;

        mockMvc.perform(put("/api/cards/2004")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk());

        mockMvc.perform(get("/api/cards/2004"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productName").value("Карта Пенсионная"))
                .andExpect(jsonPath("$.validTo").value("2032-12-31"));
    }

    @Test
    @Transactional
    @DisplayName("PUT несуществующей карты отвечает 404")
    void updateUnknownCardIs404() throws Exception {
        mockMvc.perform(put("/api/cards/999999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"productName":"x","validTo":"2031-01-01"}
                                """))
                .andExpect(status().isNotFound());
    }
}
