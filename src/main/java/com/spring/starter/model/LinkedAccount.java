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
    private long primaryAccount;
    private long secondaryAccount;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customerServiceRequestId")
    private CustomerServiceRequest customerServiceRequest;

    private boolean softReject= false;

    public LinkedAccount() {
    }

    public LinkedAccount(long cardNumber, long primaryAccount, long secondaryAccount,
                         CustomerServiceRequest customerServiceRequest, boolean softReject) {
        this.cardNumber = cardNumber;
        this.primaryAccount = primaryAccount;
        this.secondaryAccount = secondaryAccount;
        this.customerServiceRequest = customerServiceRequest;
        this.softReject = softReject;
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
        return primaryAccount;
    }

    public void setPrimaryAccount(long primaryAccount) {
        this.primaryAccount = primaryAccount;
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

