package com.example.taskapi.advice;

import com.example.taskapi.domain.ErrorDto;
import com.example.taskapi.exception.AlreadyExistedException;
import com.example.taskapi.exception.NotFoundException;
import com.example.taskapi.exception.ValidationFailedException;
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

    @ExceptionHandler(value = ValidationFailedException.class)
    public ResponseEntity<ErrorDto> validationFailedExceptionHandle(ValidationFailedException e) {
        return new ResponseEntity<>(new ErrorDto(String.valueOf(HttpStatus.BAD_REQUEST.value()), e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = AlreadyExistedException.class)
    public ResponseEntity<ErrorDto> alreadyExistedExceptionHandle(AlreadyExistedException e) {
        return new ResponseEntity<>(new ErrorDto(String.valueOf(HttpStatus.BAD_REQUEST.value()), e.getMessage()), HttpStatus.BAD_REQUEST);
    }
}
