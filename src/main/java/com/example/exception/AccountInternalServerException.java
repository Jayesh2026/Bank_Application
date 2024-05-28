package com.example.exception;

public class AccountInternalServerException extends RuntimeException {
    
    public AccountInternalServerException(String message){
        super(message);
    }

    public AccountInternalServerException(String message, Throwable cause) {
        super(message, cause);
    }
}
    