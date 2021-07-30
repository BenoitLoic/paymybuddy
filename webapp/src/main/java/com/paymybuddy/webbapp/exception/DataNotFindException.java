package com.paymybuddy.webbapp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/** Exception thrown when data do not exist in DB with HTTP 404 error. */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class DataNotFindException extends RuntimeException {
  public DataNotFindException(String message) {
    super(message);
  }
}
