package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.model.Account;
import com.example.response.SuccessResponse;
import com.example.service.AccountServiceImpl;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/account")
@Validated
public class AccountController {
    
    @Autowired
    AccountServiceImpl accountService;

    @PostMapping
    public ResponseEntity<SuccessResponse> createAccount(@Valid @RequestBody Account account){
        Account acc = accountService.createAccount(account);
        SuccessResponse successRespo = new SuccessResponse("Account Created Succesfully", HttpStatus.CREATED.value(), acc); 
        return new ResponseEntity<>(successRespo, HttpStatus.CREATED);
    }
    


}
