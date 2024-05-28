package com.example.service;

import java.util.List;
import com.example.model.Account;

public interface AccountService {

    // create user account
    public Account createAccount(Account account);

    //get account details by account number
    public Account getAccountByAccountNumber(Integer accountNumber);

    //get all account details
    public List<Account> getAll_AccountDetails();

    //get all UnDeleted accounts
    public List<Account> getAll_UnDeletedAccount();

    // get all deleted account details
    public List<Account> getAll_DeletedAccount();

    //update account balance or currency code
    public Account updateAccount(Integer accountNumber, int newBalance);

    //delete account
    public void deleteAccount(Integer accountNumber);
}
