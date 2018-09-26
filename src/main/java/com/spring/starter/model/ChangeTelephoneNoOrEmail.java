package com.spring.starter.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "change_telephone_no_or_email")
public class ChangeTelephoneNoOrEmail {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int ChangeTelephoneNoOrEmailId;
	
	private String newMobileNumber;
	
	private String newResidenceNumber;
	
	private String newOfficeNo;
	
	private String newEmail;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="csrId")
	private CustomerServiceRequest customerServiceRequest;

	public ChangeTelephoneNoOrEmail(int changeTelephoneNoOrEmailId, String newMobileNumber, String newResidenceNumber,
			String newOfficeNo, String newEmail, CustomerServiceRequest customerServiceRequest) {
		super();
		ChangeTelephoneNoOrEmailId = changeTelephoneNoOrEmailId;
		this.newMobileNumber = newMobileNumber;
		this.newResidenceNumber = newResidenceNumber;
		this.newOfficeNo = newOfficeNo;
		this.newEmail = newEmail;
		this.customerServiceRequest = customerServiceRequest;
	}

	public ChangeTelephoneNoOrEmail(int changeTelephoneNoOrEmailId, String newMobileNumber, String newResidenceNumber,
			String newOfficeNo, String newEmail) {
		super();
		ChangeTelephoneNoOrEmailId = changeTelephoneNoOrEmailId;
		this.newMobileNumber = newMobileNumber;
		this.newResidenceNumber = newResidenceNumber;
		this.newOfficeNo = newOfficeNo;
		this.newEmail = newEmail;
	}

	public ChangeTelephoneNoOrEmail() {
		super();
	}

	public int getChangeTelephoneNoOrEmailId() {
		return ChangeTelephoneNoOrEmailId;
	}

	public void setChangeTelephoneNoOrEmailId(int changeTelephoneNoOrEmailId) {
		ChangeTelephoneNoOrEmailId = changeTelephoneNoOrEmailId;
	}

	public String getNewMobileNumber() {
		return newMobileNumber;
	}

	public void setNewMobileNumber(String newMobileNumber) {
		this.newMobileNumber = newMobileNumber;
	}

	public String getNewResidenceNumber() {
		return newResidenceNumber;
	}

	public void setNewResidenceNumber(String newResidenceNumber) {
		this.newResidenceNumber = newResidenceNumber;
	}

	public String getNewOfficeNo() {
		return newOfficeNo;
	}

	public void setNewOfficeNo(String newOfficeNo) {
		this.newOfficeNo = newOfficeNo;
	}

	public String getNewEmail() {
		return newEmail;
	}

	public void setNewEmail(String newEmail) {
		this.newEmail = newEmail;
	}

	public CustomerServiceRequest getCustomerServiceRequest() {
		return customerServiceRequest;
	}

	public void setCustomerServiceRequest(CustomerServiceRequest customerServiceRequest) {
		this.customerServiceRequest = customerServiceRequest;
	}
	
}
