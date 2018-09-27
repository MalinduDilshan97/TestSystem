package com.spring.starter.model;

import javax.persistence.*;

@Entity
@Table(name = "atm_or_debitCard_request")
public class AtmOrDebitCardRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int atmOrDebitRequestid;
    private long cardNumber;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customerServiceRequestId")
    private CustomerServiceRequest customerServiceRequest;

    public AtmOrDebitCardRequest() {
    }

    public AtmOrDebitCardRequest(int atmOrDebitRequestid, long cardNumber, CustomerServiceRequest customerServiceRequest) {
        this.atmOrDebitRequestid = atmOrDebitRequestid;
        this.cardNumber = cardNumber;
        this.customerServiceRequest = customerServiceRequest;
    }

    public int getAtmOrDebitRequestid() {
        return atmOrDebitRequestid;
    }

    public void setAtmOrDebitRequestid(int atmOrDebitRequestid) {
        this.atmOrDebitRequestid = atmOrDebitRequestid;
    }

    public long getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(long cardNumber) {
        this.cardNumber = cardNumber;
    }

    public CustomerServiceRequest getCustomerServiceRequest() {
        return customerServiceRequest;
    }

    public void setCustomerServiceRequest(CustomerServiceRequest customerServiceRequest) {
        this.customerServiceRequest = customerServiceRequest;
    }
}
