package com.spring.starter.DTO;

import javax.validation.constraints.Size;
import java.io.Serializable;

public class SmsSubscriptionDTO implements Serializable {

    private int subscriptionId;
    @Size(max = 16, min = 16)
    private long cardNumber;
    private String mobileNumber;
    private int customerServiceRequestId;

    public SmsSubscriptionDTO() {
    }

    public SmsSubscriptionDTO(long cardNumber, String mobileNumber, int customerServiceRequestId) {
        this.cardNumber = cardNumber;
        this.mobileNumber = mobileNumber;
        this.customerServiceRequestId = customerServiceRequestId;
    }

    public SmsSubscriptionDTO(int subscriptionId, long cardNumber, String mobileNumber, int customerServiceRequestId) {
        this.subscriptionId = subscriptionId;
        this.cardNumber = cardNumber;
        this.mobileNumber = mobileNumber;
        this.customerServiceRequestId = customerServiceRequestId;
    }

    public int getSubscriptionId() {
        return subscriptionId;
    }

    public void setSubscriptionId(int subscriptionId) {
        this.subscriptionId = subscriptionId;
    }

    public long getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(long cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public int getCustomerServiceRequestId() {
        return customerServiceRequestId;
    }

    public void setCustomerServiceRequestId(int customerServiceRequestId) {
        this.customerServiceRequestId = customerServiceRequestId;
    }
}
