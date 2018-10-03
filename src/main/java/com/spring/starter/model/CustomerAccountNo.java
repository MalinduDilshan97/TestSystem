package com.spring.starter.model;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.lang.NonNull;

@Entity
@Table(name="customer_account_no")
public class CustomerAccountNo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int CustomerAccountNoId;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="customerId")
	private Customer customer;
	
	@NonNull
	private String accountNumber;
	
	public CustomerAccountNo() {
		super();
	}

	public CustomerAccountNo(int customerAccountNoId, Customer customer, String accountNumber) {
		super();
		CustomerAccountNoId = customerAccountNoId;
		this.customer = customer;
		this.accountNumber = accountNumber;
	}

	public int getCustomerAccountNoId() {
		return CustomerAccountNoId;
	}

	public void setCustomerAccountNoId(int customerAccountNoId) {
		CustomerAccountNoId = customerAccountNoId;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}
	
}
