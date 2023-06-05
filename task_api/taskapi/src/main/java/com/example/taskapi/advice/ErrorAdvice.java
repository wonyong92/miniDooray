package com.example.taskapi.advice;

import com.example.taskapi.domain.ErrorDto;
import com.example.taskapi.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ErrorAdvice {

    @ExceptionHandler(value = NotFoundException.class)
    public ResponseEntity<ErrorDto> notFoundExceptionHandle(NotFoundException e) {
        return new ResponseEntity<>(new ErrorDto(String.valueOf(HttpStatus.NOT_FOUND.value()), e.getMessage()), HttpStatus.NOT_FOUND);
    }
}