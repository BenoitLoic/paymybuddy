package com.paymybuddy.webbapp.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
@Slf4j
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UnicornException extends RuntimeException{
    public UnicornException(String message) {


        super(message);

    }
}
