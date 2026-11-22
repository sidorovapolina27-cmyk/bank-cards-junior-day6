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
 * Задание 4.3. Поле createdDate в CardDto: аккуратно раскатываем изменение дто.
 */
@Tag("task-4-3")
@DisplayName("Задание 4.3: createdDate в CardDto")
class Task_4_3_CreatedDateTest extends BaseIntegrationTest {

    @Test
    @Transactional
    @DisplayName("GET /api/cards/2001 отдаёт createdDate из даты создания")
    void card2001HasCreatedDate() throws Exception {
        mockMvc.perform(get("/api/cards/2001"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.createdDate").value("2024-03-01"));
    }

    @Test
    @Transactional
    @DisplayName("GET /api/cards/2004 отдаёт createdDate из даты создания")
    void card2004HasCreatedDate() throws Exception {
        mockMvc.perform(get("/api/cards/2004"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.createdDate").value("2025-01-20"));
    }

    @Test
    @Transactional
    @DisplayName("В свежевыпущенной карте createdDate — сегодняшняя дата")
    void freshCardHasTodayCreatedDate() throws Exception {
        String body = """
                {"clientId":1002,"pan":"4111111111114444","cardHolderName":"MARIA PETROVA",
                 "cardType":"CREDIT","paymentSystem":"VISA",
                 "validFrom":"2026-02-01","validTo":"2031-02-01",
                 "productName":"Карта Кредитная"}
                """;

        String response = mockMvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders
                        .post("/api/cards")
                        .contentType("application/json")
                        .content(body))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();

        String createdDate = com.jayway.jsonpath.JsonPath.read(response, "$.createdDate").toString();

        org.assertj.core.api.Assertions.assertThat(createdDate)
                .isEqualTo(java.time.LocalDate.now().toString());
    }
}
