package com.example.exception;

public class AccountNotFoundException extends RuntimeException{
    
    public AccountNotFoundException(String message, Exception ex){
        super(message, ex);
    }
}
