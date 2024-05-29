package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.model.Account;
import com.example.response.SuccessResponse;
import com.example.service.AccountServiceImpl;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/*
 * This is Controller class
 * @Valid annotation use for check valid argument pass by user or not
 */

@RestController
@RequestMapping("/account")
@Validated
public class AccountController {
    
    @Autowired
    AccountServiceImpl accountService;

    @PostMapping
    public ResponseEntity<SuccessResponse> createAccount(@Valid @RequestBody Account account){
        Account accoun = accountService.createAccount(account);
        SuccessResponse successRespo = new SuccessResponse("Account Created Successfully", HttpStatus.CREATED.value(), accoun); 
        return new ResponseEntity<>(successRespo, HttpStatus.CREATED);
    }

    @GetMapping("/{accountNumber}")
    public ResponseEntity<SuccessResponse> getAccountByAccountNumber(@Valid @PathVariable("accountNumber") Integer accountNumber){
        Account account = accountService.getAccountByAccountNumber(accountNumber);
        SuccessResponse successResponse = new SuccessResponse("Account retrieve successfully", HttpStatus.FOUND.value(), account);
        return new ResponseEntity<>(successResponse, HttpStatus.FOUND);
    }
    
    @GetMapping
    public ResponseEntity<SuccessResponse> getAllAccountDetails(){
        List<Account> accounts = accountService.getAll_AccountDetails();
        SuccessResponse successResponse = new SuccessResponse("All data retrieved successfully ", HttpStatus.OK.value(), accounts);
        return new ResponseEntity<>(successResponse, HttpStatus.OK);
    }

    @GetMapping("/unDeleted")
    public ResponseEntity<SuccessResponse> getAll_UnDeletedAccountDetails(){
        List<Account> accounts = accountService.getAll_UnDeletedAccount();
        SuccessResponse successResponse = new SuccessResponse("All Undeleted account data retrieved successfully ", HttpStatus.OK.value(), accounts);
        return new ResponseEntity<>(successResponse, HttpStatus.OK);
    }

    @GetMapping("/deleted")
    public ResponseEntity<SuccessResponse> getAll_DeletedAccountDetails(){
        List<Account> accounts = accountService.getAll_DeletedAccount();
        SuccessResponse successResponse = new SuccessResponse("All Deleted account data retrieved successfully ", HttpStatus.OK.value(), accounts);
        return new ResponseEntity<>(successResponse, HttpStatus.OK);
    }

    @PutMapping("/{accountNumber}")
    public ResponseEntity<SuccessResponse> updateAccount(@PathVariable("accountNumber") Integer accountNumber, @RequestParam("newBalance") int newBalance){
        Account accountt = accountService.updateAccount(accountNumber, newBalance);
        SuccessResponse successResponse = new SuccessResponse("Your data is updated successfully", HttpStatus.OK.value(), accountt);
        return new ResponseEntity<>(successResponse, HttpStatus.OK);
    }

    @DeleteMapping("/{accountNumber}")
    public ResponseEntity<SuccessResponse> deleteAccount(@PathVariable("accountNumber") Integer accountNumber){
        accountService.deleteAccount(accountNumber);
        SuccessResponse successResponse = new SuccessResponse();
            successResponse.setMessage("Account deleted successfully");
            successResponse.setStatusCode(HttpStatus.OK.value());
        return new ResponseEntity<>(successResponse, HttpStatus.OK);
    }
}
