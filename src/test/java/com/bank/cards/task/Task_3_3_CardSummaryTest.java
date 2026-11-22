package com.bank.cards.task;

import com.bank.cards.support.BaseIntegrationTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Задание 3.3. Плитка карты: маска PAN и вычисляемые поля.
 */
@Tag("task-3-3")
@DisplayName("Задание 3.3: GET /api/cards/{id}/summary — маска и вычисления")
class Task_3_3_CardSummaryTest extends BaseIntegrationTest {

    @Test
    @Transactional
    @DisplayName("Маска PAN: 4 звезды, пробел и последние 4 цифры")
    void maskedPanFormat() throws Exception {
        mockMvc.perform(get("/api/cards/2001/summary"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.maskedPan").value("**** 1001"));
    }

    @Test
    @Transactional
    @DisplayName("Маска для карты другого клиента")
    void maskedPanAnotherCard() throws Exception {
        mockMvc.perform(get("/api/cards/2002/summary"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.maskedPan").value("**** 1002"));
    }

    @Test
    @Transactional
    @DisplayName("Активная карта: expired=false, daysToExpiry считается от сегодня до validTo")
    void activeCardNotExpired() throws Exception {
        long expectedDays = ChronoUnit.DAYS.between(LocalDate.now(), LocalDate.of(2029, 3, 1));

        mockMvc.perform(get("/api/cards/2001/summary"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.expired").value(false))
                .andExpect(jsonPath("$.daysToExpiry").value(expectedDays))
                .andExpect(jsonPath("$.status").value("ACTIVE"));
    }

    @Test
    @Transactional
    @DisplayName("Просроченная карта (2003): expired=true, daysToExpiry отрицательный")
    void expiredCardIsFlagged() throws Exception {
        mockMvc.perform(get("/api/cards/2003/summary"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.expired").value(true))
                .andExpect(jsonPath("$.daysToExpiry").value(org.hamcrest.Matchers.lessThan(0)));
    }
}
