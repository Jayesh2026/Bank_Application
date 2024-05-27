package com.example.exception;


import java.util.Map;
import java.util.HashMap;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
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

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        for(FieldError error : ex.getBindingResult().getFieldErrors()){
            errors.put(error.getField(), error.getDefaultMessage());
        }
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
    
}
