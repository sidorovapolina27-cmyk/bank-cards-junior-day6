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
 * Задание 6.3. Валюта карты: NOT NULL с бэкфиллом.
 */
@Tag("task-6-3")
@DisplayName("Задание 6.3: currency_code — бэкфилл и NOT NULL")
class Task_6_3_CurrencyCodeTest extends BaseIntegrationTest {

    @Test
    @Transactional
    @DisplayName("Карта «Мир» (2001) — RUB")
    void mirCardIsRub() throws Exception {
        mockMvc.perform(get("/api/cards/2001"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.currencyCode").value("RUB"));
    }

    @Test
    @Transactional
    @DisplayName("Карта Visa (2006) — USD")
    void visaCardIsUsd() throws Exception {
        mockMvc.perform(get("/api/cards/2006"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.currencyCode").value("USD"));
    }

    @Test
    @Transactional
    @DisplayName("Карта Mastercard (2003) — EUR")
    void mastercardCardIsEur() throws Exception {
        mockMvc.perform(get("/api/cards/2003"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.currencyCode").value("EUR"));
    }

    @Test
    @Transactional
    @DisplayName("Выпуск карты Visa без валюты подставляет USD")
    void createVisaWithoutCurrencyGetsUsd() throws Exception {
        String body = """
                {"clientId":1002,"pan":"4111111111116666","cardHolderName":"MARIA PETROVA",
                 "cardType":"DEBIT","paymentSystem":"VISA",
                 "validFrom":"2026-02-01","validTo":"2031-02-01",
                 "productName":"Карта Классическая"}
                """;

        mockMvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders
                        .post("/api/cards")
                        .contentType("application/json")
                        .content(body))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.currencyCode").value("USD"));
    }

    @Test
    @Transactional
    @DisplayName("Выпуск карты с явной валютой сохраняет её")
    void createWithExplicitCurrency() throws Exception {
        String body = """
                {"clientId":1003,"pan":"2200000000005555","cardHolderName":"ALEXEY SIDOROV",
                 "cardType":"DEBIT","paymentSystem":"MIR","currencyCode":"KZT",
                 "validFrom":"2026-02-01","validTo":"2031-02-01",
                 "productName":"Карта Валютная"}
                """;

        mockMvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders
                        .post("/api/cards")
                        .contentType("application/json")
                        .content(body))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.currencyCode").value("KZT"));
    }
}
