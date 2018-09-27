package com.spring.starter.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.lang.NonNull;

@Entity
@Table(name = "change_mailing_mail")
public class ChangeMailingMailModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int ChangeMailingMailId;

	@NotNull
	@NotEmpty
	private String newMailingAddress;

	@NonNull
	@NotEmpty
	private String city;

	@NonNull
	@NotEmpty
	private String postalCode;

	@NonNull
	@NotEmpty
	private String stateOrProvince;

	@OneToMany(fetch = FetchType.LAZY)
	@JoinColumn(name = "ChangeMailingMailId")
	private List<MailingMailAccountNumbers> mailingMailAccountNo;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="csrId")
	private CustomerServiceRequest customerServiceRequest;
	

	public ChangeMailingMailModel() {
		super();
	}

	public ChangeMailingMailModel(int changeMailingMailId, @NotNull @NotEmpty String newMailingAddress,
			@NotEmpty String city, @NotEmpty String postalCode, @NotEmpty String stateOrProvince,
			List<MailingMailAccountNumbers> mailingMailAccountNo, CustomerServiceRequest customerServiceRequest) {
		super();
		ChangeMailingMailId = changeMailingMailId;
		this.newMailingAddress = newMailingAddress;
		this.city = city;
		this.postalCode = postalCode;
		this.stateOrProvince = stateOrProvince;
		this.mailingMailAccountNo = mailingMailAccountNo;
		this.customerServiceRequest = customerServiceRequest;
	}

	public List<MailingMailAccountNumbers> getMailingMailAccountNo() {
		return mailingMailAccountNo;
	}

	public void setMailingMailAccountNo(List<MailingMailAccountNumbers> mailingMailAccountNo) {
		this.mailingMailAccountNo = mailingMailAccountNo;
	}

	public ChangeMailingMailModel(@NotNull @NotEmpty String newMailingAddress, @NotEmpty String city,
			@NotEmpty String postalCode, @NotEmpty String stateOrProvince) {
		super();
		this.newMailingAddress = newMailingAddress;
		this.city = city;
		this.postalCode = postalCode;
		this.stateOrProvince = stateOrProvince;
	}

	public ChangeMailingMailModel(int changeMailingMailId, @NotNull @NotEmpty String newMailingAddress,
			@NotEmpty String city, @NotEmpty String postalCode, @NotEmpty String stateOrProvince) {
		super();
		ChangeMailingMailId = changeMailingMailId;
		this.newMailingAddress = newMailingAddress;
		this.city = city;
		this.postalCode = postalCode;
		this.stateOrProvince = stateOrProvince;
	}

	public int getChangeMailingMailId() {
		return ChangeMailingMailId;
	}

	public void setChangeMailingMailId(int changeMailingMailId) {
		ChangeMailingMailId = changeMailingMailId;
	}

	public String getNewMailingAddress() {
		return newMailingAddress;
	}

	public void setNewMailingAddress(String newMailingAddress) {
		this.newMailingAddress = newMailingAddress;
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

	public CustomerServiceRequest getCustomerServiceRequest() {
		return customerServiceRequest;
	}

	public void setCustomerServiceRequest(CustomerServiceRequest customerServiceRequest) {
		this.customerServiceRequest = customerServiceRequest;
	}

}
