package com.spring.starter.DTO;

import java.io.Serializable;

public class PosLimitDTO implements Serializable {

    private int posLimitId;
    private long cardNumber;
    private double value;
    private int customerServiceRequestId;

    public PosLimitDTO() {
    }

    public PosLimitDTO(long cardNumber, double value, int customerServiceRequestId) {
        this.cardNumber = cardNumber;
        this.value = value;
        this.customerServiceRequestId = customerServiceRequestId;
    }

    public PosLimitDTO(int posLimitId, long cardNumber, double value, int customerServiceRequestId) {
        this.posLimitId = posLimitId;
        this.cardNumber = cardNumber;
        this.value = value;
        this.customerServiceRequestId = customerServiceRequestId;
    }

    public int getPosLimitId() {
        return posLimitId;
    }

    public void setPosLimitId(int posLimitId) {
        this.posLimitId = posLimitId;
    }

    public long getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(long cardNumber) {
        this.cardNumber = cardNumber;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public int getCustomerServiceRequestId() {
        return customerServiceRequestId;
    }

    public void setCustomerServiceRequestId(int customerServiceRequestId) {
        this.customerServiceRequestId = customerServiceRequestId;
    }
}
