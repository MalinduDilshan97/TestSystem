package com.spring.starter.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "posLimit")
public class PosLimit implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int posLimitId;
    private long cardNumber;
    private double value;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customerServiceRequestId")
    private CustomerServiceRequest customerServiceRequest;

    public PosLimit() {
    }

    public PosLimit(int posLimitId, long cardNumber, double value, CustomerServiceRequest customerServiceRequest) {
        this.posLimitId = posLimitId;
        this.cardNumber = cardNumber;
        this.value = value;
        this.customerServiceRequest = customerServiceRequest;
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

    public CustomerServiceRequest getCustomerServiceRequest() {
        return customerServiceRequest;
    }

    public void setCustomerServiceRequest(CustomerServiceRequest customerServiceRequest) {
        this.customerServiceRequest = customerServiceRequest;
    }
}
