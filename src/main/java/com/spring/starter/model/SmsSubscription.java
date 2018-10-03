package com.spring.starter.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "sms_subscription")
public class SmsSubscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int subscriptionId;
    private long cardNumber;
    private String mobileNumber;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customerServiceRequestId")
    private CustomerServiceRequest customerServiceRequest;

    public SmsSubscription() {
    }

    public SmsSubscription(int subscriptionId, long cardNumber, String mobileNumber, CustomerServiceRequest customerServiceRequest) {
        this.subscriptionId = subscriptionId;
        this.cardNumber = cardNumber;
        this.mobileNumber = mobileNumber;
        this.customerServiceRequest = customerServiceRequest;
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

    public CustomerServiceRequest getCustomerServiceRequest() {
        return customerServiceRequest;
    }

    public void setCustomerServiceRequest(CustomerServiceRequest customerServiceRequest) {
        this.customerServiceRequest = customerServiceRequest;
    }
}
