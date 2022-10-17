package ru.klimov.backend.model.dto.jwt;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Используется для ответа при авторизации пользователя через JWT
 */
@Data
@AllArgsConstructor
public class JwtResponseDto {
    private String token;
}
