package ru.klimov.backend.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Используется в качестве ответа на http запрос, в котором возникло исключение
 */
@Getter
@Setter
@AllArgsConstructor
public class ExceptionMessage {
    String message;
}
