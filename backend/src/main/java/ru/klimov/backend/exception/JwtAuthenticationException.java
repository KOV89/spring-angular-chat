package ru.klimov.backend.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;

/**
 * Исключение используется для ошибок аутентификации и авторизациит.
 */
@Slf4j
public class JwtAuthenticationException extends AuthenticationException {
    public JwtAuthenticationException(String msg, Throwable t) {
        super(msg, t);
        log.error(msg);
    }

    public JwtAuthenticationException(String msg) {
        super(msg);
        log.error(msg);
    }
}