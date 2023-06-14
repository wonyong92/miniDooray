package com.nhn.academy.minidooray.gateway.exception.handler;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;

@org.springframework.web.bind.annotation.ControllerAdvice
public class ControllerAdvice {

  @ExceptionHandler(HttpClientErrorException.class)
  public ResponseEntity<String> exception(HttpClientErrorException ex) {
    return ResponseEntity.status(ex.getStatusCode()).body(ex.getMessage());
  }
}
