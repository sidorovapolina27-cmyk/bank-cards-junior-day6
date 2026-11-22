package com.bank.cards.task;

import com.bank.cards.support.BaseIntegrationTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Задание 4.6. Basic auth для административных эндпоинтов.
 */
@Tag("task-4-6")
@DisplayName("Задание 4.6: фильтр basic auth для /api/admin/**")
class Task_4_6_AdminAuthFilterTest extends BaseIntegrationTest {

    @Test
    void pingWithoutCredentialsIs401() throws Exception {
        mockMvc.perform(get("/api/admin/ping"))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.code").value("UNAUTHORIZED"))
                .andExpect(jsonPath("$.message", containsString("аутентификация")));
    }

    @Test
    void pingWithWrongCredentialsIs401() throws Exception {
        mockMvc.perform(get("/api/admin/ping")
                        .header("Authorization", basic("hacker:hacker123")))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.code").value("UNAUTHORIZED"));
    }

    @Test
    void pingWithValidCredentialsIs200() throws Exception {
        mockMvc.perform(get("/api/admin/ping")
                        .header("Authorization", basic("admin:admin123")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("ok"));
    }

    private String basic(String loginPassword) {
        return "Basic " + java.util.Base64.getEncoder()
                .encodeToString(loginPassword.getBytes(java.nio.charset.StandardCharsets.UTF_8));
    }
}
