package com.example.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.exception.*;
import com.example.model.Account;
import com.example.repository.AccountRepository;

/*
 * Service layer
 */

@Service
public class AccountServiceImpl implements AccountService{
    
    @Autowired
    AccountRepository accountRepo;

    // create account
    @Override
    public Account createAccount(Account account){
        boolean exist = accountRepo.existsByAccountNumber(account.getAccountNumber());
        if(exist){
            throw new AccountNumberAlreadyExistsException("This account number already exits");
        }
        return accountRepo.save(account);
    }

    // get account data by account number
    @Override
    public Account getAccountByAccountNumber(Integer accountNumber) {
        Optional<Account> account = accountRepo.findByAccountNumber(accountNumber);

        if(!account.isPresent()){
            throw new AccountNotFoundException("This account is not present.");
        }
        return account.get();
    }

    // get all account details
    @Override
    public List<Account> getAll_AccountDetails()  {
        List<Account> accounts = accountRepo.findAll();
        if (accounts == null || accounts.isEmpty()) {
            throw new AccountNotFoundException("Unable to retrieved accounts details, Not found any account.");
        }
        return accounts;
    }

    // get all deleted account details
    @Override
    public List<Account> getAll_DeletedAccount(){
        List<Account> deletedAccounts = accountRepo.findByDeleted(true);
        if (deletedAccounts == null || deletedAccounts.isEmpty()) {
            throw new AccountNotFoundException("Unable to retrieved deleted accounts details, Not found any deleted account.");
        }
        return deletedAccounts;
    }

    
    //get all UnDeleted accounts
    @Override
    public List<Account> getAll_UnDeletedAccount(){
        List<Account> unDeletedAccounts = accountRepo.findByDeleted(false);
        if (unDeletedAccounts == null || unDeletedAccounts.isEmpty()) {
            throw new AccountNotFoundException("Unable to retrieved Undeleted accounts details, Not found any Undeleted account.");
        }
        return unDeletedAccounts;
    }

    

    // delete account but not from database
    @Override
    public void deleteAccount(Integer accountNumber){
        Optional<Account> account = accountRepo.findByAccountNumber(accountNumber);

        if(account.isPresent()){
            Account acc = account.get();
            acc.setDeleted(true);

            accountRepo.save(acc);
        }else{
            throw new AccountNotFoundException("This account number not found");
        }
    }

    // Update account balance 
    @Override
    public Account updateAccount(Integer accountNumber, int newBalance) {
        Optional<Account> acc = accountRepo.findByAccountNumber(accountNumber);
        if(acc.isPresent()){
            Account account = acc.get();
            if(account.isDeleted()){
                throw new AccountNotFoundException("This account balance cannot be updated, because is not present or deleted.");
            }
            newBalance =  account.getBalance() + newBalance;
            account.setBalance(newBalance);
            return accountRepo.save(account);
        }else{
            throw new AccountNotFoundException("This account number not found");
        }
    }


}
