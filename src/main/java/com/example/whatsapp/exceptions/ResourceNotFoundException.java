package com.example.whatsapp.exceptions;

public class ResourceNotFoundException extends RuntimeException{
    public ResourceNotFoundException() {
        super();
    }
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
