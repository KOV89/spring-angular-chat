package ru.klimov.backend.model.dto.jwt;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * Запрос при авторизации пользователя через JWT
 */
@Data
public class JwtRequestDto {
    @NotNull(message = "Не задано поле username")
    private String username;
    @NotNull(message = "Не задано поле password")
    private String password;
}
