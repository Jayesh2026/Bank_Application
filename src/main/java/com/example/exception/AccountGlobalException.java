package com.example.exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.example.response.ErrorResponse;

@ControllerAdvice
public class AccountGlobalException extends Exception {
    
    @ExceptionHandler(AccountNumberAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleAccountNumberAlreadyExistsException(AccountNumberAlreadyExistsException acExist){
        ErrorResponse errorResponse = new ErrorResponse(acExist.getMessage(), HttpStatus.CONFLICT.value());
        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }

    
}
