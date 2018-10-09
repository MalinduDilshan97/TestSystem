package com.spring.starter.DTO;

import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class SMSAlertsForCreditCardDTO {

	@NotNull
	private String mobileNumber;
	
	@NotNull
	@Size(min = 16,max = 16)
	private List<String> creditCardNumbers;

	public SMSAlertsForCreditCardDTO() {
		super();
	}

	public SMSAlertsForCreditCardDTO(@NotNull String mobileNumber, @NotNull List<String> creditCardNumbers) {
		super();
		this.mobileNumber = mobileNumber;
		this.creditCardNumbers = creditCardNumbers;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public List<String> getCreditCardNumbers() {
		return creditCardNumbers;
	}

	public void setCreditCardNumbers(List<String> creditCardNumbers) {
		this.creditCardNumbers = creditCardNumbers;
	}
	
}
