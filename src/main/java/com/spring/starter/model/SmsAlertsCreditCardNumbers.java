package com.spring.starter.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "sms_alerts_credit_card_numbers")
public class SmsAlertsCreditCardNumbers {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int smsAlertsCreditCardNumbers;
	
	@NotNull
	private String creditCardNumber;

	public SmsAlertsCreditCardNumbers() {
		super();
	}

	public SmsAlertsCreditCardNumbers(@NotNull String creditCardNumber) {
		this.creditCardNumber = creditCardNumber;
	}

	public int getSmsAlertsCreditCardNumbers() {
		return smsAlertsCreditCardNumbers;
	}

	public void setSmsAlertsCreditCardNumbers(int smsAlertsCreditCardNumbers) {
		this.smsAlertsCreditCardNumbers = smsAlertsCreditCardNumbers;
	}

	public String getCreditCardNumber() {
		return creditCardNumber;
	}

	public void setCreditCardNumber(String creditCardNumber) {
		this.creditCardNumber = creditCardNumber;
	}
		
}
