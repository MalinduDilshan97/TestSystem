package com.spring.starter.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="internet_banking_exclude_account_numbers")
public class InternetBankingExcludeAccountNumbers {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int internetBankingExcludeAccountNumbersId;

	@NotNull
	private String accountNumber;

	public InternetBankingExcludeAccountNumbers() {
		super();
	}

	public InternetBankingExcludeAccountNumbers(int internetBankingExcludeAccountNumbersId,
			@NotNull String accountNumber) {
		super();
		this.internetBankingExcludeAccountNumbersId = internetBankingExcludeAccountNumbersId;
		this.accountNumber = accountNumber;
	}

	public int getInternetBankingExcludeAccountNumbersId() {
		return internetBankingExcludeAccountNumbersId;
	}

	public void setInternetBankingExcludeAccountNumbersId(int internetBankingExcludeAccountNumbersId) {
		this.internetBankingExcludeAccountNumbersId = internetBankingExcludeAccountNumbersId;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}
}
