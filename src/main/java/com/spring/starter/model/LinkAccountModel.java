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
@Table(name="link_account_model ")
public class LinkAccountModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int linkAccountModelId;
	
	@NotNull
	private String existingBankingUserId;
	
	@OneToMany(fetch = FetchType.LAZY)
	@JoinColumn(name="internetBankingId")
	@NotNull
	@NotEmpty
	private List<InternetBankingLinkAccountNumbers> internetBankingLinkAccountNumbers;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="csrId")
	private CustomerServiceRequest customerServiceRequest;

	private boolean softReject= false;

	public LinkAccountModel() {
		super();
	}
	
	public LinkAccountModel(int linkAccountModelId, @NotNull String existingBankingUserId,
			@NotNull @NotEmpty List<InternetBankingLinkAccountNumbers> internetBankingLinkAccountNumbers,
			CustomerServiceRequest customerServiceRequest) {
		super();
		this.linkAccountModelId = linkAccountModelId;
		this.existingBankingUserId = existingBankingUserId;
		this.internetBankingLinkAccountNumbers = internetBankingLinkAccountNumbers;
		this.customerServiceRequest = customerServiceRequest;
	}



	public LinkAccountModel(int linkAccountModelId, @NotNull String existingBankingUserId,
			@NotNull @NotEmpty List<InternetBankingLinkAccountNumbers> internetBankingLinkAccountNumbers) {
		super();
		this.linkAccountModelId = linkAccountModelId;
		this.existingBankingUserId = existingBankingUserId;
		this.internetBankingLinkAccountNumbers = internetBankingLinkAccountNumbers;
	}

	public boolean isSoftReject() {
		return softReject;
	}

	public void setSoftReject(boolean softReject) {
		this.softReject = softReject;
	}

	public int getLinkAccountModelId() {
		return linkAccountModelId;
	}

	public void setLinkAccountModelId(int linkAccountModelId) {
		this.linkAccountModelId = linkAccountModelId;
	}

	public String getExistingBankingUserId() {
		return existingBankingUserId;
	}

	public void setExistingBankingUserId(String existingBankingUserId) {
		this.existingBankingUserId = existingBankingUserId;
	}

	public List<InternetBankingLinkAccountNumbers> getInternetBankingLinkAccountNumbers() {
		return internetBankingLinkAccountNumbers;
	}

	public void setInternetBankingLinkAccountNumbers(
			List<InternetBankingLinkAccountNumbers> internetBankingLinkAccountNumbers) {
		this.internetBankingLinkAccountNumbers = internetBankingLinkAccountNumbers;
	}

	public CustomerServiceRequest getCustomerServiceRequest() {
		return customerServiceRequest;
	}

	public void setCustomerServiceRequest(CustomerServiceRequest customerServiceRequest) {
		this.customerServiceRequest = customerServiceRequest;
	}
	
}
