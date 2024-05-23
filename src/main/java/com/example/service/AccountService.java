package com.example.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.exception.AccountNumberAlreadyExistsException;
import com.example.model.Account;
import com.example.repository.AccountRepository;

@Service
public class AccountService {
    
    @Autowired
    AccountRepository accountRepo;

    public Account createAccount(Account account){
        Optional<Account> existingAcc = accountRepo.findByAccountNumber(account.getAccountNumber());
        if(existingAcc.isPresent()){
            throw new AccountNumberAlreadyExistsException(account.getAccountNumber());
        }
        return accountRepo.save(account);
    }

}
