package com.example.model;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Account")
public class Account {

    @Id
    @GeneratedValue
    @Column(name = "Account UUID")
    private UUID accountId;

    private Integer accountNumber;
    private String accountType;
    private String iso4217Currency;
    private Integer balance;

    private boolean deleted = Boolean.FALSE;

}
