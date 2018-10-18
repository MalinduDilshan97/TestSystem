package com.spring.starter.model;

import java.util.Date;
import java.util.List;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.lang.NonNull;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;


@Entity
@Table(name="customer")
public class Customer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int customerId;
	
	@NonNull
	private String name;
	
	@NonNull
	@Column
	private String identification;
	
	@NonNull
	private String mobileNo;

	@NotNull
	private Date date;

	@OneToMany(fetch = FetchType.LAZY)
	@JoinColumn(name="customerId")
	private List<CustomerAccountNo> accountNo;
	
	@OneToMany(fetch = FetchType.LAZY)
	@JoinColumn(name="customerServiceRequestsId")
	@JsonIgnore
	private List<CustomerServiceRequest> customerServiceRequests;

	private int status;

	public Customer() {
		super();
	}

	public int getCustomerId() {
		return customerId;
	}

	public Customer(int customerId, String name, String identification, String mobileNo,
			List<CustomerAccountNo> accountNo, List<CustomerServiceRequest> customerServiceRequests) {
		super();
		this.customerId = customerId;
		this.name = name;
		this.identification = identification;
		this.mobileNo = mobileNo;
		this.accountNo = accountNo;
		this.customerServiceRequests = customerServiceRequests;
	}

	public Customer(int customerId, String name, String identification, String mobileNo,
			List<CustomerAccountNo> accountNo) {
		super();
		this.customerId = customerId;
		this.name = name;
		this.identification = identification;
		this.mobileNo = mobileNo;
		this.accountNo = accountNo;
	}

	public Customer(int customerId, String name, String identification, List<CustomerAccountNo> accountNo) {
		super();
		this.customerId = customerId;
		this.name = name;
		this.identification = identification;
		this.accountNo = accountNo;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIdentification() {
		return identification;
	}

	public void setIdentification(String identificatin) {
		this.identification = identificatin;
	}

	public List<CustomerAccountNo> getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(List<CustomerAccountNo> accountNo) {
		this.accountNo = accountNo;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public List<CustomerServiceRequest> getCustomerServiceRequests() {
		return customerServiceRequests;
	}

	public void setCustomerServiceRequests(List<CustomerServiceRequest> customerServiceRequests) {
		this.customerServiceRequests = customerServiceRequests;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
}
