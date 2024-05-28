package com.example.model;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
 * This class is entity class
 * contain all field related to database column
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Account")
public class Account {

    @Id
    @GeneratedValue
    @Column(name = "Account UUID")
    private UUID accountId;

    @NotNull(message = "Account number can not nbe null.")
    @Column(name = "account_number", nullable = false)
    private Integer accountNumber;

    @Size(min = 3, max = 10 , message = "Account type can not be null")
    @Column(name = "account_type", nullable = false, columnDefinition = "VARCHAR(10) DEFAULT 'saving'")
    private String accountType = "saving";

    @Pattern(regexp = "^[A-Z]{3}$", message = "Please enter 3 character of Currency code")
    @Column(name = "iso4217_currency_code")
    private String iso4217Currency;

    @Column(columnDefinition = "INTEGER DEFAULT 0", nullable = false)
    private int balance;

    private boolean deleted = false;

}
