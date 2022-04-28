package ru.hogwarts.school.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.hogwarts.school.exception.AlreadyAddedException;
import ru.hogwarts.school.exception.NotFoundException;
import ru.hogwarts.school.exception.ValidatorException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = AlreadyAddedException.class)
    public ResponseEntity<String> alreadyAddedException(AlreadyAddedException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = ValidatorException.class)
    public ResponseEntity<String> validatorException(ValidatorException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = NotFoundException.class)
    public ResponseEntity<String> notFoundException(NotFoundException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
    }
}