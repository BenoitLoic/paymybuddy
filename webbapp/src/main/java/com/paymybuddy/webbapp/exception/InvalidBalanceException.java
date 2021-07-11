package com.paymybuddy.webbapp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidBalanceException extends RuntimeException {
  public InvalidBalanceException(String message) {

    super(message);
  }
}