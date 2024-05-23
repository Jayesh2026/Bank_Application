package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.model.Account;
import com.example.response.SuccessResponse;
import com.example.service.AccountService;

@RestController
@RequestMapping("/account")
public class AccountController {
    
    @Autowired
    AccountService accountService;

    @PostMapping
    public ResponseEntity<SuccessResponse> createAccount(@RequestBody Account account){
        Account acc = accountService.createAccount(account);
        SuccessResponse successRespo = new SuccessResponse("Account Created Succesfully", HttpStatus.CREATED.value(), acc); 
        return new ResponseEntity<>(successRespo, HttpStatus.CREATED);
    }
    

}
