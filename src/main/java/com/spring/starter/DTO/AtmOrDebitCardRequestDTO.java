package com.spring.starter.DTO;

import java.io.Serializable;

public class AtmOrDebitCardRequestDTO implements Serializable {

    private int atmOrDebitRequestId;
    private long cardNumber;
    private int customerServiceRequestId;

    public AtmOrDebitCardRequestDTO() {
    }

    public AtmOrDebitCardRequestDTO(long cardNumber, int customerServiceRequestId) {
        this.cardNumber = cardNumber;
        this.customerServiceRequestId = customerServiceRequestId;
    }

    public AtmOrDebitCardRequestDTO(int atmOrDebitRequestId, long cardNumber, int customerServiceRequestId) {
        this.atmOrDebitRequestId = atmOrDebitRequestId;
        this.cardNumber = cardNumber;
        this.customerServiceRequestId = customerServiceRequestId;
    }

    public int getAtmOrDebitRequestId() {
        return atmOrDebitRequestId;
    }

    public void setAtmOrDebitRequestId(int atmOrDebitRequestId) {
        this.atmOrDebitRequestId = atmOrDebitRequestId;
    }

    public long getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(long cardNumber) {
        this.cardNumber = cardNumber;
    }

    public int getCustomerServiceRequestId() {
        return customerServiceRequestId;
    }

    public void setCustomerServiceRequestId(int customerServiceRequestId) {
        this.customerServiceRequestId = customerServiceRequestId;
    }
}
