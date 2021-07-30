package com.paymybuddy.webbapp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception thrown when contact is not part of user contact list with HTTP 409 error. ex: attempt
 * to create transfer with deleted contact .
 */
@ResponseStatus(HttpStatus.CONFLICT)
public class IllegalContactException extends RuntimeException {

  public IllegalContactException(String message) {
    super(message);
  }
}
