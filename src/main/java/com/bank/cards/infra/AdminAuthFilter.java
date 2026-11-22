package com.bank.cards.infra;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * Базовая аутентификация для административных эндпоинтов (/api/admin/**).
 * Разрешённые пары логин:пароль задаются свойством app.admin-credentials.
 * Без корректного заголовка Authorization — 401 с машинно-читаемым телом.
 */
@Component
@Order(2)
public class AdminAuthFilter extends OncePerRequestFilter {

    private static final String PREFIX = "Basic ";

    private final String adminCredentials;

    public AdminAuthFilter(@org.springframework.beans.factory.annotation.Value("${app.admin-credentials}") String adminCredentials) {
        this.adminCredentials = adminCredentials;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        if (!request.getRequestURI().startsWith("/api/admin/")) {
            filterChain.doFilter(request, response);
            return;
        }

        String authorization = request.getHeader("Authorization");
        if (authorization != null && authorization.startsWith(PREFIX) && isKnownCredential(authorization.substring(PREFIX.length()))) {
            filterChain.doFilter(request, response);
            return;
        }

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.getWriter().write(
                "{\"code\":\"UNAUTHORIZED\",\"message\":\"Требуется базовая аутентификация\"}");
    }

    private boolean isKnownCredential(String base64Credentials) {
        try {
            String decoded = new String(Base64.getDecoder().decode(base64Credentials), StandardCharsets.UTF_8);
            for (String known : adminCredentials.split(",")) {
                if (decoded.equals(known.trim())) {
                    return true;
                }
            }
            return false;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}
