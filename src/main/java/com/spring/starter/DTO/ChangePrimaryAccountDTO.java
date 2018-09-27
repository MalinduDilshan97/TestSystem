package com.spring.starter.DTO;

public class ChangePrimaryAccountDTO {

    private int changePrimaryAccountId;
    private long cardNumber;
    private long accountNumber;
    private int customerServiceRequestId;

    public ChangePrimaryAccountDTO() {
    }

    public ChangePrimaryAccountDTO(int changePrimaryAccountId, long cardNumber, long accountNumber, int customerServiceRequestId) {
        this.changePrimaryAccountId = changePrimaryAccountId;
        this.cardNumber = cardNumber;
        this.accountNumber = accountNumber;
        this.customerServiceRequestId = customerServiceRequestId;
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

    public int getCustomerServiceRequestId() {
        return customerServiceRequestId;
    }

    public void setCustomerServiceRequestId(int customerServiceRequestId) {
        this.customerServiceRequestId = customerServiceRequestId;
    }


}
