package com.bank.cards.task;

import com.bank.cards.support.BaseIntegrationTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Задание 4.5. Сквозной идентификатор запроса X-Request-Id.
 */
@Tag("task-4-5")
@DisplayName("Задание 4.5: фильтр X-Request-Id + MDC")
class Task_4_5_RequestIdFilterTest extends BaseIntegrationTest {

    @Test
    void requestIdIsGenerated() throws Exception {
        mockMvc.perform(get("/api/cards/2001"))
                .andExpect(status().isOk())
                .andExpect(header().exists("X-Request-Id"))
                .andExpect(result ->
                        assertThat(result.getResponse().getHeader("X-Request-Id")).isNotBlank());
    }

    @Test
    void incomingRequestIdIsPreserved() throws Exception {
        mockMvc.perform(get("/api/cards/2001").header("X-Request-Id", "test-req-42"))
                .andExpect(status().isOk())
                .andExpect(result ->
                        assertThat(result.getResponse().getHeader("X-Request-Id")).isEqualTo("test-req-42"));
    }
}
