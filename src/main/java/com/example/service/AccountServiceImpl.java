package com.example.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.exception.AccountNumberAlreadyExistsException;
import com.example.model.Account;
import com.example.repository.AccountRepository;

@Service
public class AccountServiceImpl implements AccountService{
    
    @Autowired
    AccountRepository accountRepo;

    @Override
    public Account createAccount(Account account){
        Optional<Account> existingAcc = accountRepo.findByAccountNumber(account.getAccountNumber());
        if(existingAcc.isPresent()){
            throw new AccountNumberAlreadyExistsException("Account already exits with this number: "+account.getAccountNumber());
        }
        return accountRepo.save(account);
    }

    



}
