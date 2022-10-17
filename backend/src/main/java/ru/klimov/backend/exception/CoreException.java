package ru.klimov.backend.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CoreException extends Exception {
    public CoreException (String message) {
        super(message);
        log.error(message);
    }
}
