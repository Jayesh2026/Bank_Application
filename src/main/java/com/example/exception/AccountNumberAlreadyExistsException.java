package com.example.exception;

public class AccountNumberAlreadyExistsException extends RuntimeException{
    
    public AccountNumberAlreadyExistsException(Integer accNo){
        super("Account already exits with this number: "+ accNo);
    }

    public AccountNumberAlreadyExistsException(String mssg, Exception exp){
        super(mssg, exp);
    }
}
