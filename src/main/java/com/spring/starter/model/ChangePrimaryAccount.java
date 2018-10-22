package com.spring.starter.model;

import javax.persistence.*;

@Entity
@Table(name = "change_primary_account")
public class ChangePrimaryAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int changePrimaryAccountId;
    private long cardNumber;
    private long accountNumber;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customerServiceRequestId")
    private CustomerServiceRequest customerServiceRequest;

    private boolean softReject= false;

    public ChangePrimaryAccount() {
    }

    public ChangePrimaryAccount(int changePrimaryAccountId, long cardNumber, long accountNumber, CustomerServiceRequest customerServiceRequest) {
        this.changePrimaryAccountId = changePrimaryAccountId;
        this.cardNumber = cardNumber;
        this.accountNumber = accountNumber;
        this.customerServiceRequest = customerServiceRequest;
    }

    public int getChangePrimaryAccountId() {
        return changePrimaryAccountId;
    }

    public void setChangePrimaryAccountId(int changePrimaryAccountId) {
        this.changePrimaryAccountId = changePrimaryAccountId;
    }

    public long getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(long cardNumber) {
        this.cardNumber = cardNumber;
    }

    public long getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(long accountNumber) {
        this.accountNumber = accountNumber;
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
