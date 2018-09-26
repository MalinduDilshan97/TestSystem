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
@Table(name = "change_identification_form")
public class ChangeIdentificationForm {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int ChangeIdentificationFormId;
	
	@NotNull
	private String identification;
	
	@NotNull
	private String documantUrl;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="csrId")
	private CustomerServiceRequest customerServiceRequest;

	public ChangeIdentificationForm() {
		super();
	}

	public ChangeIdentificationForm(int changeIdentificationFormId, @NotNull String identification,
			@NotNull String documantUrl, CustomerServiceRequest customerServiceRequest) {
		super();
		ChangeIdentificationFormId = changeIdentificationFormId;
		this.identification = identification;
		this.documantUrl = documantUrl;
		this.customerServiceRequest = customerServiceRequest;
	}

	public ChangeIdentificationForm(int changeIdentificationFormId, @NotNull String identification,
			@NotNull String documantUrl) {
		super();
		ChangeIdentificationFormId = changeIdentificationFormId;
		this.identification = identification;
		this.documantUrl = documantUrl;
	}

	public int getChangeIdentificationFormId() {
		return ChangeIdentificationFormId;
	}

	public void setChangeIdentificationFormId(int changeIdentificationFormId) {
		ChangeIdentificationFormId = changeIdentificationFormId;
	}

	public String getIdentification() {
		return identification;
	}

	public void setIdentification(String identification) {
		this.identification = identification;
	}

	public String getDocumantUrl() {
		return documantUrl;
	}

	public void setDocumantUrl(String documantUrl) {
		this.documantUrl = documantUrl;
	}

	public CustomerServiceRequest getCustomerServiceRequest() {
		return customerServiceRequest;
	}

	public void setCustomerServiceRequest(CustomerServiceRequest customerServiceRequest) {
		this.customerServiceRequest = customerServiceRequest;
	}

}
