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
    private int SmsAlertsCreditCardNumbers;

    @NotNull
    private String creditCardNumber;

    public SmsAlertsCreditCardNumbers() {
        super();
    }

    public SmsAlertsCreditCardNumbers(int smsAlertsCreditCardNumbers, @NotNull String creditCardNumber) {
        super();
        SmsAlertsCreditCardNumbers = smsAlertsCreditCardNumbers;
        this.creditCardNumber = creditCardNumber;
    }

    public int getSmsAlertsCreditCardNumbers() {
        return SmsAlertsCreditCardNumbers;
    }

    public void setSmsAlertsCreditCardNumbers(int smsAlertsCreditCardNumbers) {
        SmsAlertsCreditCardNumbers = smsAlertsCreditCardNumbers;
    }

    public String getCreditCardNumber() {
        return creditCardNumber;
    }

    public void setCreditCardNumber(String creditCardNumber) {
        this.creditCardNumber = creditCardNumber;
    }

}
