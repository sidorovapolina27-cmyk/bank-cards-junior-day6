package com.bank.cards.task;

import com.bank.cards.card.CardRepository;
import com.bank.cards.support.BaseIntegrationTest;
import com.bank.cards.support.CardTestDataFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Задание 2.1. Выпуск новой карты.
 */
@Tag("task-2-1")
@DisplayName("Задание 2.1: POST /api/cards выпускает карту")
class Task_2_1_CreateCardTest extends BaseIntegrationTest {

    @Autowired
    private CardRepository cardRepository;

    @Test
    @Transactional
    @DisplayName("POST /api/cards отвечает 201 и возвращает карту со статусом NEW")
    void createCardReturns201AndNewStatus() throws Exception {
        String body = objectMapper.writeValueAsString(CardTestDataFactory.createCardRequest());

        mockMvc.perform(post("/api/cards")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.clientId").value(1001))
                .andExpect(jsonPath("$.pan").value("2200000000007777"))
                .andExpect(jsonPath("$.status").value("NEW"))
                .andExpect(jsonPath("$.productName").value("Карта Классическая"))
                .andExpect(jsonPath("$.validFrom").value("2026-01-01"))
                .andExpect(jsonPath("$.validTo").value("2031-01-01"));
    }

    @Test
    @Transactional
    @DisplayName("Созданная карта доступна через GET /api/cards/{id}")
    void createdCardIsReadableBack() throws Exception {
        String body = """
                {"clientId":1002,"pan":"4111111111118888","cardHolderName":"MARIA PETROVA",
                 "cardType":"CREDIT","paymentSystem":"VISA",
                 "validFrom":"2026-02-01","validTo":"2031-02-01",
                 "productName":"Карта Кредитная"}
                """;

        String response = mockMvc.perform(post("/api/cards")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();

        String id = com.jayway.jsonpath.JsonPath.read(response, "$.id").toString();

        mockMvc.perform(get("/api/cards/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.pan").value("4111111111118888"))
                .andExpect(jsonPath("$.cardHolderName").value("MARIA PETROVA"))
                .andExpect(jsonPath("$.status").value("NEW"));
    }

    @Test
    @Transactional
    @DisplayName("Карта действительно сохранена в репозиторий со всеми полями")
    void cardIsPersisted() throws Exception {
        String body = """
                {"clientId":1003,"pan":"5555555555559999","cardHolderName":"ALEXEY SIDOROV",
                 "cardType":"DEBIT","paymentSystem":"MASTERCARD",
                 "validFrom":"2026-03-01","validTo":"2031-03-01",
                 "productName":"Карта Цифровая"}
                """;

        String response = mockMvc.perform(post("/api/cards")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();

        long id = Long.parseLong(com.jayway.jsonpath.JsonPath.read(response, "$.id").toString());

        assertThat(cardRepository.findById(id))
                .hasValueSatisfying(card -> {
                    assertThat(card.getClientId()).isEqualTo(1003L);
                    assertThat(card.getPan()).isEqualTo("5555555555559999");
                    assertThat(card.getCardHolderName()).isEqualTo("ALEXEY SIDOROV");
                    assertThat(card.getStatus()).isEqualTo(com.bank.cards.card.CardStatus.NEW);
                    assertThat(card.getValidFrom()).isEqualTo(java.time.LocalDate.of(2026, 3, 1));
                    assertThat(card.getValidTo()).isEqualTo(java.time.LocalDate.of(2031, 3, 1));
                    assertThat(card.getProductName()).isEqualTo("Карта Цифровая");
                    assertThat(card.getCreatedAt()).isNotNull();
                });
    }
}
