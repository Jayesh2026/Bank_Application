package com.example.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.exception.AccountNotFoundException;
import com.example.exception.AccountNumberAlreadyExistsException;
import com.example.model.Account;
import com.example.repository.AccountRepository;

@ExtendWith(MockitoExtension.class)
public class AccountServiceTest {
    
    @Mock
    AccountRepository accountRepo;

    @InjectMocks
    AccountServiceImpl accountServiceImp;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateAccount(){
        Account newAccount = new Account();
        newAccount.setAccountNumber(123456789);

        when(accountRepo.existsByAccountNumber(newAccount.getAccountNumber())).thenReturn(false);
        when(accountRepo.save(newAccount)).thenReturn(newAccount);

        Account createdAccount = accountServiceImp.createAccount(newAccount);

        assertNotNull(createdAccount);
        assertEquals(newAccount, createdAccount);

        verify(accountRepo).existsByAccountNumber(newAccount.getAccountNumber());
        verify(accountRepo).save(newAccount);
    }

    @Test
    public void testGetAccount_ByAccountNumber(){
        Integer accountNumber = 12345678;
        Account account = new Account();
        account.setAccountNumber(accountNumber);

        when(accountRepo.findByAccountNumber(accountNumber)).thenReturn(Optional.of(account));

        Account retrieveAccount = accountServiceImp.getAccountByAccountNumber(accountNumber);

        verify(accountRepo).findByAccountNumber(accountNumber);
        assertEquals(account, retrieveAccount);
    }

    @Test
    public void getAll_AccountDetails(){
        List<Account> accountList = new ArrayList<>();
        accountList.add(new Account(UUID.randomUUID(), 12345678, "saving", "USD", 100, true));
        accountList.add(new Account(UUID.randomUUID(), 646654, "credit", "INR", 100, false));
        accountList.add(new Account(UUID.randomUUID(), 216444465, "saving", "USD", 5000, false));

        when(accountRepo.findAll()).thenReturn(accountList);

        List<Account> retrievedAccount = accountServiceImp.getAll_AccountDetails();

        verify(accountRepo).findAll();

        assertNotNull(retrievedAccount);
        assertEquals(accountList, retrievedAccount);
    }

    @Test
    public void getAll_Undeleted_AccountDetails(){
        List<Account> accountList = new ArrayList<>();
        accountList.add(new Account(UUID.randomUUID(), 12345678, "saving", "USD", 100, false));
        accountList.add(new Account(UUID.randomUUID(), 646654, "credit", "INR", 100, false));
        accountList.add(new Account(UUID.randomUUID(), 216444465, "saving", "USD", 5000, false));

        when(accountRepo.findByDeleted(false)).thenReturn(accountList);

        List<Account> retrieve_UndeletedAccount = accountServiceImp.getAll_UnDeletedAccount();

        verify(accountRepo).findByDeleted(false);

        assertNotNull(retrieve_UndeletedAccount);
        assertEquals(accountList, retrieve_UndeletedAccount);
    }

    @Test
    public void getAll_Deleted_AccountDetails(){
        List<Account> accountList = new ArrayList<>();
        accountList.add(new Account(UUID.randomUUID(), 12345678, "saving", "USD", 100, false));
        accountList.add(new Account(UUID.randomUUID(), 646654, "credit", "INR", 100, true));
        accountList.add(new Account(UUID.randomUUID(), 216444465, "saving", "USD", 5000, false));

        when(accountRepo.findByDeleted(true)).thenReturn(accountList);

        List<Account> retrieve_UndeletedAccount = accountServiceImp.getAll_DeletedAccount();

        verify(accountRepo).findByDeleted(true);

        assertNotNull(retrieve_UndeletedAccount);
        assertEquals(accountList, retrieve_UndeletedAccount);
    }

    @Test
    public void test_UpdateAccount(){
        Integer accountNumber = 123456;
        Account existingAccount = new Account();
        existingAccount.setAccountNumber(accountNumber);
        existingAccount.setBalance(1000); // old balance
        existingAccount.setAccountType("saving");
        existingAccount.setIso4217Currency("INR");

        Account updateAccount = new Account();
        updateAccount.setAccountNumber(accountNumber);
        updateAccount.setBalance(500); // New balance
        updateAccount.setAccountType("credit");
        updateAccount.setIso4217Currency("USD");

        when(accountRepo.findByAccountNumber(accountNumber)).thenReturn(Optional.of(existingAccount));
        when(accountRepo.save(any(Account.class))).thenReturn(updateAccount);

        Account updatedAccount = accountServiceImp.updateAccount(updateAccount);

        verify(accountRepo).findByAccountNumber(accountNumber);
        verify(accountRepo).save(existingAccount);

        assertEquals(updateAccount.getBalance(), updatedAccount.getBalance());
        assertEquals(updateAccount.getAccountType(), updatedAccount.getAccountType());
        assertEquals(updateAccount.getIso4217Currency(), updatedAccount.getIso4217Currency());
    }

    @Test
    public void deleteAccount(){
        Integer accountNumber = 123456478;
        Account account = new Account();
        when(accountRepo.findByAccountNumber(accountNumber)).thenReturn(Optional.of(account));

        accountServiceImp.deleteAccount(accountNumber);
        
        verify(accountRepo).findByAccountNumber(accountNumber);
        verify(accountRepo).save(account);
        assert account.isDeleted();
    }


// ====================================== Negative Test Cases ============================================

    // account already exits
    @Test
    public void testCreateAccount_AccountNumberAlreadyExists() {
        Account existingAccount = new Account();
        existingAccount.setAccountNumber(123456789);

        when(accountRepo.existsByAccountNumber(existingAccount.getAccountNumber())).thenReturn(true);

        // Throwing AccountNumberAlreadyExitsException
        assertThrows(AccountNumberAlreadyExistsException.class, () -> {
            accountServiceImp.createAccount(existingAccount);
        });

        verify(accountRepo).existsByAccountNumber(existingAccount.getAccountNumber());
        verify(accountRepo, never()).save(existingAccount);
    }

    // Not found account by Account_Number
    @Test
    public void testGetAccount_ByAccountNumber_AccountNotFound(){
        Integer accountNumber = 12345678;

        when(accountRepo.findByAccountNumber(accountNumber)).thenReturn(Optional.empty());

        //Throwing AccountNotFoundException
        assertThrows(AccountNotFoundException.class, () ->{
            accountServiceImp.getAccountByAccountNumber(accountNumber);
        });

        verify(accountRepo).findByAccountNumber(accountNumber);
    }

    // Not any account found, trying to retrieve account all accounts
    @Test
    public void testGetAll_AccountDetails_NoAccountsFound() {
        List<Account> emptyList = new ArrayList<>();
        when(accountRepo.findAll()).thenReturn(emptyList);

        assertThrows(AccountNotFoundException.class, () -> {
            accountServiceImp.getAll_AccountDetails();
        });

        verify(accountRepo).findAll();
    }

    // Not found any Undeleted_Account
    @Test
    public void testGetAll_UndeletedAccountDetails_NoAccountsFound(){
        List<Account> emptyList = new ArrayList<>();
        when(accountRepo.findByDeleted(false)).thenReturn(emptyList);

        assertThrows(AccountNotFoundException.class, () -> {
            accountServiceImp.getAll_UnDeletedAccount();
        });

        verify(accountRepo).findByDeleted(false);
    }

    // Not found any Deleted_Account
    @Test
    public void testGetAll_DeletedAccountDetails_NoAccountsFound(){
        List<Account> emptyList = new ArrayList<>();
        when(accountRepo.findByDeleted(true)).thenReturn(emptyList);

        assertThrows(AccountNotFoundException.class, () -> {
            accountServiceImp.getAll_DeletedAccount();
        });

        verify(accountRepo).findByDeleted(true);
    }

}
