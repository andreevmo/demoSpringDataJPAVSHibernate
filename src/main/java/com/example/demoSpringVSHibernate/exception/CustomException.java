package com.example.demoSpringVSHibernate.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.NoSuchElementException;

import static org.springframework.http.HttpStatus.*;

@RestController
@ControllerAdvice
public class CustomException {

    @ResponseStatus(INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public String generalExceptionHandler(Exception exception) {
        return exception.getMessage();
    }

    @ResponseStatus(NOT_FOUND)
    @ExceptionHandler(NoSuchElementException.class)
    public String noSuchElementExceptionHandler(NoSuchElementException exception) {
        return exception.getMessage();
    }

    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler({HttpMessageNotReadableException.class})
    public String validationExceptionsHandler(Exception exception) {
        return exception.getMessage();
    }

    @ResponseStatus(UNPROCESSABLE_ENTITY)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public String validationExceptionsHandler(MethodArgumentNotValidException exception) {
        return exception.getMessage();
    }

    @ResponseStatus(UNPROCESSABLE_ENTITY)
    @ExceptionHandler(DataIntegrityViolationException.class)
    public String validationExceptionsHandler(DataIntegrityViolationException exception) {
        return exception.getMessage();
    }
}
