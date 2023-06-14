package com.nhn.academy.minidooray.gateway.exception.handler;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

@org.springframework.web.bind.annotation.ControllerAdvice
public class ControllerAdvice {

  @ExceptionHandler({HttpServerErrorException.class})
  public ResponseEntity<String> exception(HttpServerErrorException ex) {
    return ResponseEntity.status(ex.getStatusCode()).body(ex.getMessage());
  }

  @ExceptionHandler({HttpClientErrorException.class})
  public ResponseEntity<String> exception2(HttpClientErrorException ex) {
    return ResponseEntity.status(ex.getStatusCode()).body(ex.getMessage());
  }
}
