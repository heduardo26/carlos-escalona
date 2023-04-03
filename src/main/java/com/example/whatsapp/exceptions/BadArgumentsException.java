package com.example.whatsapp.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class BadArgumentsException extends RuntimeException {

    public BadArgumentsException(String message) {
        super(message);
    }

    public BadArgumentsException() {
        super();
    }
}