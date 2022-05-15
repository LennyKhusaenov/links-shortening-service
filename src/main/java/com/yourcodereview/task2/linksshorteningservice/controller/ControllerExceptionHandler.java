package com.yourcodereview.task2.linksshorteningservice.controller;

import com.yourcodereview.task2.linksshorteningservice.api.model.ErrorResponse;
import com.yourcodereview.task2.linksshorteningservice.repository.EntityAlreadyExistsException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.Instant;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.apache.commons.lang3.StringUtils.SPACE;

@Slf4j
@ControllerAdvice
public class ControllerExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({MethodArgumentTypeMismatchException.class, EntityAlreadyExistsException.class})
    @ResponseBody
    public ErrorResponse handleBadRequest(RuntimeException ex) {
        log.error(ex.getMessage());
        return new ErrorResponse()
                .message(ex.getMessage())
                .timestamp(Instant.now());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ErrorResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        var fieldErrors = ex.getFieldErrors().stream()
                .map(fieldError -> fieldError.getField() + SPACE + fieldError.getDefaultMessage());
        var globalErrors = ex.getGlobalErrors().stream()
                .map(objectError -> objectError.getObjectName() + SPACE + objectError.getDefaultMessage());
        var allMessages = Stream.concat(fieldErrors, globalErrors)
                .collect(Collectors.joining("; "));
        return new ErrorResponse()
                .message(allMessages)
                .timestamp(Instant.now());
    }
}
