package ru.klimov.backend.controller;

import org.hibernate.exception.ConstraintViolationException;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.klimov.backend.exception.JwtAuthenticationException;
import ru.klimov.backend.exception.NotFoundException;
import ru.klimov.backend.exception.ExceptionMessage;

import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Обработчик ошибок по всем контроллерам (для возврата "читаемого" текста ошибки (ExceptionResponse))
 */
@ControllerAdvice
public class GlobalExceptionController {

    @ExceptionHandler({NotFoundException.class, UsernameNotFoundException.class})
    public ResponseEntity<ExceptionMessage> notFoundExceptionHandler(@NotNull Exception exception) {
        return new ResponseEntity<>(
                getExceptionResponse(exception),
                HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler({PropertyReferenceException.class, JwtAuthenticationException.class})
    public ResponseEntity<ExceptionMessage> propertyExceptionHandler(@NotNull Exception exception) {
        return new ResponseEntity<>(
                getExceptionResponse(exception),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler({ConstraintViolationException.class})
    public ResponseEntity<ExceptionMessage> constraintErrorHandler(@NotNull ConstraintViolationException exception) {
        return new ResponseEntity<>(
                new ExceptionMessage(exception.getSQLException().getLocalizedMessage()),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionMessage> methodArgumentNotValidExceptionHandler(@NotNull MethodArgumentNotValidException exception) {
        return new ResponseEntity<>(
                new ExceptionMessage(
                        exception.getBindingResult().getAllErrors().stream()
                                .map(ObjectError::getDefaultMessage)
                                .collect(Collectors.toList()).toString()
                        ),
                HttpStatus.BAD_REQUEST
        );
    }

    private @NotNull ExceptionMessage getExceptionResponse(@NotNull Exception e) {
        return new ExceptionMessage(Optional.of(e.getMessage()).orElse(e.getClass().getSimpleName()));
    }
}
