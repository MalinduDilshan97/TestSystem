package com.spring.starter.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "internet_banking_account_numbers")
public class InternetBankingLinkAccountNumbers {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int internetBankingAccountNumbersId;

    @NotNull
    private String accountNumber;


    public InternetBankingLinkAccountNumbers() {
        super();
    }

    public InternetBankingLinkAccountNumbers(int internetBankingAccountNumbersId, @NotNull String accountNumber) {
        super();
        this.internetBankingAccountNumbersId = internetBankingAccountNumbersId;
        this.accountNumber = accountNumber;
    }

    public int getInternetBankingAccountNumbersId() {
        return internetBankingAccountNumbersId;
    }

    public void setInternetBankingAccountNumbersId(int internetBankingAccountNumbersId) {
        this.internetBankingAccountNumbersId = internetBankingAccountNumbersId;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }


}
