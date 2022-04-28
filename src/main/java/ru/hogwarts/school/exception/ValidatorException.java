package ru.hogwarts.school.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ValidatorException extends RuntimeException {
    public ValidatorException() {
        super();
    }

    public ValidatorException(String message) {
        super(message);
    }

    public ValidatorException(String message, Throwable t) {
        super(message, t);
    }

    public ValidatorException(Throwable t) {
        super(t);
    }
}
