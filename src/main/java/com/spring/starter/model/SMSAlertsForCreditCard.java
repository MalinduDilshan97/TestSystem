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
import javax.validation.constraints.NotNull;


@Entity
@Table(name = "sms_alerts_for_credit_card")
public class SMSAlertsForCreditCard {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int SMSAlertsForCreditCardId;
	
	@NotNull
	private String mobileNumber;
	
	@OneToMany(fetch = FetchType.LAZY)
	@JoinColumn(name="SMSAlertsForCreditCardId")
	private List<SmsAlertsCreditCardNumbers> SMSAlertsForCreditCard;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="csrId")
	private CustomerServiceRequest customerServiceRequest;

	private boolean softReject = false;

	public SMSAlertsForCreditCard() {
		super();
	}

	public SMSAlertsForCreditCard(int sMSAlertsForCreditCardId, @NotNull String mobileNumber,
			List<SmsAlertsCreditCardNumbers> sMSAlertsForCreditCard, CustomerServiceRequest customerServiceRequest) {
		super();
		SMSAlertsForCreditCardId = sMSAlertsForCreditCardId;
		this.mobileNumber = mobileNumber;
		SMSAlertsForCreditCard = sMSAlertsForCreditCard;
		this.customerServiceRequest = customerServiceRequest;
	}

	public int getSMSAlertsForCreditCardId() {
		return SMSAlertsForCreditCardId;
	}

	public void setSMSAlertsForCreditCardId(int sMSAlertsForCreditCardId) {
		SMSAlertsForCreditCardId = sMSAlertsForCreditCardId;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public List<SmsAlertsCreditCardNumbers> getSMSAlertsForCreditCard() {
		return SMSAlertsForCreditCard;
	}

	public void setSMSAlertsForCreditCard(List<SmsAlertsCreditCardNumbers> sMSAlertsForCreditCard) {
		SMSAlertsForCreditCard = sMSAlertsForCreditCard;
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
