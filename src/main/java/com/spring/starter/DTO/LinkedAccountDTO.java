package com.spring.starter.DTO;

import java.io.Serializable;

public class LinkedAccountDTO implements Serializable {

    private int linkedAccountId;
    private long cardNumber;
    private long PrimaryAccount;
    private long secondaryAccount;
    private int customerServiceRequestId;

    public LinkedAccountDTO() {
    }

    public LinkedAccountDTO(long cardNumber, long primaryAccount, long secondaryAccount, int customerServiceRequestId) {
        this.cardNumber = cardNumber;
        PrimaryAccount = primaryAccount;
        this.secondaryAccount = secondaryAccount;
        this.customerServiceRequestId = customerServiceRequestId;
    }

    public LinkedAccountDTO(int linkedAccountId, long cardNumber, long primaryAccount, long secondaryAccount, int customerServiceRequestId) {
        this.linkedAccountId = linkedAccountId;
        this.cardNumber = cardNumber;
        PrimaryAccount = primaryAccount;
        this.secondaryAccount = secondaryAccount;
        this.customerServiceRequestId = customerServiceRequestId;
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

    public int getCustomerServiceRequestId() {
        return customerServiceRequestId;
    }

    public void setCustomerServiceRequestId(int customerServiceRequestId) {
        this.customerServiceRequestId = customerServiceRequestId;
    }
}
