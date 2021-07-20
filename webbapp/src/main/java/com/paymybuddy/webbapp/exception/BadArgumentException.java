package com.paymybuddy.webbapp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * This exception is thrown when the argument given is incorrect with HTTP 400 error.
 * ex : user try to add a negative value to his balance.
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadArgumentException extends RuntimeException {
  public BadArgumentException(String message) {
    super(message);
  }
}
