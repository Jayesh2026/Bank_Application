package com.example.exception;

public class AccountInternalServerException extends Exception {
    
    public AccountInternalServerException(String mssg, Exception ex){
        super(mssg, ex);
    }
}
