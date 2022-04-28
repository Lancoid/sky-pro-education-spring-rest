package ru.hogwarts.school.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class AlreadyAddedException extends RuntimeException {
    public AlreadyAddedException() {
        super();
    }

    public AlreadyAddedException(String message) {
        super(message);
    }

    public AlreadyAddedException(String message, Throwable t) {
        super(message, t);
    }

    public AlreadyAddedException(Throwable t) {
        super(t);
    }
}
