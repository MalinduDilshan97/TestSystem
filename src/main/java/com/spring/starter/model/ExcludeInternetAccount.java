package com.spring.starter.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "exclude_internet_account")
public class ExcludeInternetAccount {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int excludeInternetAccountId;
	
	@NotEmpty
	@NotEmpty
	private String existingBankingUserId;
	
	@OneToMany(fetch = FetchType.LAZY)
	@JoinColumn(name="internetBankingId")
	@NotEmpty
	@NotNull
	private List<InternetBankingExcludeAccountNumbers> bankingExcludeAccountNumbers;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="csrId")
	private CustomerServiceRequest customerServiceRequest;

	public ExcludeInternetAccount() {
		super();
	}

	public ExcludeInternetAccount(int excludeInternetAccountId, @NotEmpty @NotEmpty String existingBankingUserId,
			@NotEmpty @NotNull List<InternetBankingExcludeAccountNumbers> bankingExcludeAccountNumbers,
			CustomerServiceRequest customerServiceRequest) {
		super();
		this.excludeInternetAccountId = excludeInternetAccountId;
		this.existingBankingUserId = existingBankingUserId;
		this.bankingExcludeAccountNumbers = bankingExcludeAccountNumbers;
		this.customerServiceRequest = customerServiceRequest;
	}



	public CustomerServiceRequest getCustomerServiceRequest() {
		return customerServiceRequest;
	}



	public void setCustomerServiceRequest(CustomerServiceRequest customerServiceRequest) {
		this.customerServiceRequest = customerServiceRequest;
	}



	public ExcludeInternetAccount(int excludeInternetAccountId, @NotEmpty @NotEmpty String existingBankingUserId,
			@NotEmpty @NotNull List<InternetBankingExcludeAccountNumbers> bankingExcludeAccountNumbers) {
		super();
		this.excludeInternetAccountId = excludeInternetAccountId;
		this.existingBankingUserId = existingBankingUserId;
		this.bankingExcludeAccountNumbers = bankingExcludeAccountNumbers;
	}

	public int getExcludeInternetAccountId() {
		return excludeInternetAccountId;
	}

	public void setExcludeInternetAccountId(int excludeInternetAccountId) {
		this.excludeInternetAccountId = excludeInternetAccountId;
	}

	public String getExistingBankingUserId() {
		return existingBankingUserId;
	}

	public void setExistingBankingUserId(String existingBankingUserId) {
		this.existingBankingUserId = existingBankingUserId;
	}

	public List<InternetBankingExcludeAccountNumbers> getBankingExcludeAccountNumbers() {
		return bankingExcludeAccountNumbers;
	}

	public void setBankingExcludeAccountNumbers(List<InternetBankingExcludeAccountNumbers> bankingExcludeAccountNumbers) {
		this.bankingExcludeAccountNumbers = bankingExcludeAccountNumbers;
	}
}
