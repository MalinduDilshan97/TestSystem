package com.spring.starter.model;

import javax.persistence.*;

@Entity
@Table(name = "atm_or_debitCard_request")
public class AtmOrDebitCardRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int atmOrDebitRequestId;
    private long cardNumber;
    private String requestType;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customerServiceRequestId")
    private CustomerServiceRequest customerServiceRequest;

    public AtmOrDebitCardRequest() {
    }

    public AtmOrDebitCardRequest(long cardNumber, String requestType, CustomerServiceRequest customerServiceRequest) {
        this.cardNumber = cardNumber;
        this.requestType = requestType;
        this.customerServiceRequest = customerServiceRequest;
    }

    public AtmOrDebitCardRequest(int atmOrDebitRequestId, long cardNumber, String requestType, CustomerServiceRequest customerServiceRequest) {
        this.atmOrDebitRequestId = atmOrDebitRequestId;
        this.cardNumber = cardNumber;
        this.requestType = requestType;
        this.customerServiceRequest = customerServiceRequest;
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

    public String getRequestType() {
        return requestType;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }

    public CustomerServiceRequest getCustomerServiceRequest() {
        return customerServiceRequest;
    }

    public void setCustomerServiceRequest(CustomerServiceRequest customerServiceRequest) {
        this.customerServiceRequest = customerServiceRequest;
    }
}
