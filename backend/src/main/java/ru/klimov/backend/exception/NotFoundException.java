package ru.klimov.backend.exception;
/**
 * Исключение используется при обращении к несуществующим объектам
 */
public class NotFoundException extends CoreException {
    public NotFoundException(String message) {
        super(message);
    }
}
