package com.spring.starter.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "linkedAccount")
public class LinkedAccount implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int linkedAccountId;
    private long cardNumber;
    private long PrimaryAccount;
    private long secondaryAccount;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customerServiceRequestId")
    private CustomerServiceRequest customerServiceRequest;

    private boolean softReject= false;

    public LinkedAccount() {
    }

    public LinkedAccount(int linkedAccountId, long cardNumber, long primaryAccount, long secondaryAccount, CustomerServiceRequest customerServiceRequest) {
        this.linkedAccountId = linkedAccountId;
        this.cardNumber = cardNumber;
        PrimaryAccount = primaryAccount;
        this.secondaryAccount = secondaryAccount;
        this.customerServiceRequest = customerServiceRequest;
    }

    public int getLinkedAccountId() {
        return linkedAccountId;
    }

    public void setLinkedAccountId(int linkedAccountId) {
        this.linkedAccountId = linkedAccountId;
    }

    public long getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(long cardNumber) {
        this.cardNumber = cardNumber;
    }

    public long getPrimaryAccount() {
        return PrimaryAccount;
    }

    public void setPrimaryAccount(long primaryAccount) {
        PrimaryAccount = primaryAccount;
    }

    public long getSecondaryAccount() {
        return secondaryAccount;
    }

    public void setSecondaryAccount(long secondaryAccount) {
        this.secondaryAccount = secondaryAccount;
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

