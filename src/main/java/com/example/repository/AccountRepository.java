package com.example.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.model.Account;

import java.util.List;
import java.util.Optional;


@Repository
public interface AccountRepository extends JpaRepository<Account, UUID>{
    
    public Optional<Account> findByAccountNumber(Integer accNo);
    
    public Boolean existsByAccountNumber(Integer accountNumber);

    public List<Account> findByDeleted(boolean bool);

}
