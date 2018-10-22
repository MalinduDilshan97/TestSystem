package com.spring.starter.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "change_permanent_mail")
public class ChangePermanentMail {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int ChangePermanentMailId;
	
	private String newPermanentAddress;

	private String city;

	private String postalCode;
	

	private String stateOrProvince;
	

	private String country;
	
	private String documentUrl;
	
	private String documentType;
	
	private String issuingAuthority;
	
	private String placeOfIssue;

	private boolean softReject = false;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="csrId")
	private CustomerServiceRequest customerServiceRequest;

	public ChangePermanentMail() {
		super();
	}
	
	public ChangePermanentMail(int changePermanentMailId, @NotNull String newPermanentAddress, @NotNull String city,
			@NotNull String postalCode, @NotNull String stateOrProvince, @NotNull String country,
			@NotNull String documentUrl, @NotNull String documentType, @NotNull String issuingAuthority,
			@NotNull String placeOfIssue, CustomerServiceRequest customerServiceRequest) {
		super();
		ChangePermanentMailId = changePermanentMailId;
		this.newPermanentAddress = newPermanentAddress;
		this.city = city;
		this.postalCode = postalCode;
		this.stateOrProvince = stateOrProvince;
		this.country = country;
		this.documentUrl = documentUrl;
		this.documentType = documentType;
		this.issuingAuthority = issuingAuthority;
		this.placeOfIssue = placeOfIssue;
		this.customerServiceRequest = customerServiceRequest;
	}

	public ChangePermanentMail(int changePermanentMailId, @NotNull String newPermanentAddress, @NotNull String city,
			@NotNull String postalCode, @NotNull String stateOrProvince, @NotNull String country,
			@NotNull String documentUrl, @NotNull String documentType, @NotNull String issuingAuthority,
			@NotNull String placeOfIssue) {
		super();
		ChangePermanentMailId = changePermanentMailId;
		this.newPermanentAddress = newPermanentAddress;
		this.city = city;
		this.postalCode = postalCode;
		this.stateOrProvince = stateOrProvince;
		this.country = country;
		this.documentUrl = documentUrl;
		this.documentType = documentType;
		this.issuingAuthority = issuingAuthority;
		this.placeOfIssue = placeOfIssue;
	}

	public int getChangePermanentMailId() {
		return ChangePermanentMailId;
	}

	public void setChangePermanentMailId(int changePermanentMailId) {
		ChangePermanentMailId = changePermanentMailId;
	}

	public String getNewPermanentAddress() {
		return newPermanentAddress;
	}

	public void setNewPermanentAddress(String newPermanentAddress) {
		this.newPermanentAddress = newPermanentAddress;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String getStateOrProvince() {
		return stateOrProvince;
	}

	public void setStateOrProvince(String stateOrProvince) {
		this.stateOrProvince = stateOrProvince;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getDocumentType() {
		return documentType;
	}

	public void setDocumentType(String documentType) {
		this.documentType = documentType;
	}

	public String getIssuingAuthority() {
		return issuingAuthority;
	}

	public void setIssuingAuthority(String issuingAuthority) {
		this.issuingAuthority = issuingAuthority;
	}

	public String getPlaceOfIssue() {
		return placeOfIssue;
	}

	public void setPlaceOfIssue(String placeOfIssue) {
		this.placeOfIssue = placeOfIssue;
	}

	public String getDocumentUrl() {
		return documentUrl;
	}

	public void setDocumentUrl(String documentUrl) {
		this.documentUrl = documentUrl;
	}

	public CustomerServiceRequest getCustomerServiceRequest() {
		return customerServiceRequest;
	}

	public void setCustomerServiceRequest(CustomerServiceRequest customerServiceRequest) {
		this.customerServiceRequest = customerServiceRequest;
	}

	public boolean isSoftReject() {
		return softReject;
	}

	public void setSoftReject(boolean softReject) {
		this.softReject = softReject;
	}
}
