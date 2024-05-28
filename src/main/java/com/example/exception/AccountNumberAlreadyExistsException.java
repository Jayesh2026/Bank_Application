package com.example.exception;

public class AccountNumberAlreadyExistsException extends RuntimeException{
    
    public AccountNumberAlreadyExistsException(String message){
        super(message);
    }
}
