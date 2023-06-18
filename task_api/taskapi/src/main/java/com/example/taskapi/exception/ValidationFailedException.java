package com.example.taskapi.exception;

import org.springframework.validation.BindingResult;

import java.util.stream.Collectors;

public class ValidationFailedException extends RuntimeException{
    public ValidationFailedException(BindingResult bindingResult) {
        super(bindingResult.getAllErrors()
                .stream()
                .map(error -> new StringBuilder()
                        .append("Message=").append(error.getDefaultMessage())
                        .append(",code=").append(error.getCode()))
                .collect(Collectors.joining(" | ")));
    }
}
