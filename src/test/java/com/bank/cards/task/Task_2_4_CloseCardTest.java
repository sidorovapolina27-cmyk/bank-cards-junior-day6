package com.bank.cards.task;

import com.bank.cards.card.CardRepository;
import com.bank.cards.support.BaseIntegrationTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Задание 2.4. Закрытие карты (soft delete).
 */
@Tag("task-2-4")
@DisplayName("Задание 2.4: DELETE /api/cards/{id} закрывает, а не удаляет")
class Task_2_4_CloseCardTest extends BaseIntegrationTest {

    @Autowired
    private CardRepository cardRepository;

    @Test
    @Transactional
    @DisplayName("DELETE активной карты отвечает 204")
    void deleteActiveCardReturns204() throws Exception {
        mockMvc.perform(delete("/api/cards/2008"))
                .andExpect(status().isNoContent());
    }

    @Test
    @Transactional
    @DisplayName("Карта остаётся в базе со статусом CLOSED")
    void cardStaysInDbWithClosedStatus() throws Exception {
        mockMvc.perform(delete("/api/cards/2008"))
                .andExpect(status().isNoContent());

        assertThat(cardRepository.existsById(2008L)).isTrue();

        mockMvc.perform(get("/api/cards/2008"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("CLOSED"));
    }

    @Test
    @Transactional
    @DisplayName("Повторное DELETE закрытой карты отвечает 409")
    void secondDeleteIs409() throws Exception {
        mockMvc.perform(delete("/api/cards/2008"))
                .andExpect(status().isNoContent());

        mockMvc.perform(delete("/api/cards/2008"))
                .andExpect(status().isConflict());
    }

    @Test
    @Transactional
    @DisplayName("DELETE уже закрытой карты из сида (2003) сразу отвечает 409")
    void deleteAlreadyClosedSeedCardIs409() throws Exception {
        mockMvc.perform(delete("/api/cards/2003"))
                .andExpect(status().isConflict());
    }

    @Test
    @Transactional
    @DisplayName("DELETE несуществующей карты отвечает 404")
    void deleteUnknownCardIs404() throws Exception {
        mockMvc.perform(delete("/api/cards/999999"))
                .andExpect(status().isNotFound());
    }
}
