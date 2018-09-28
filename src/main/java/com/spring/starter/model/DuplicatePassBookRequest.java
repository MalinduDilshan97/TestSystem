package com.spring.starter.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "duplicate_passBook_request")
public class DuplicatePassBookRequest implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int DuplicatePassBookRequestId;
    private long AccountNumber;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customerServiceRequestId")
    private CustomerServiceRequest customerServiceRequest;

    public DuplicatePassBookRequest() {
    }

    public DuplicatePassBookRequest(int duplicatePassBookRequestId, long accountNumber, CustomerServiceRequest customerServiceRequest) {
        DuplicatePassBookRequestId = duplicatePassBookRequestId;
        AccountNumber = accountNumber;
        this.customerServiceRequest = customerServiceRequest;
    }

    public int getDuplicatePassBookRequestId() {
        return DuplicatePassBookRequestId;
    }

    public void setDuplicatePassBookRequestId(int duplicatePassBookRequestId) {
        DuplicatePassBookRequestId = duplicatePassBookRequestId;
    }

    public long getAccountNumber() {
        return AccountNumber;
    }

    public void setAccountNumber(long accountNumber) {
        AccountNumber = accountNumber;
    }

    public CustomerServiceRequest getCustomerServiceRequest() {
        return customerServiceRequest;
    }

    public void setCustomerServiceRequest(CustomerServiceRequest customerServiceRequest) {
        this.customerServiceRequest = customerServiceRequest;
    }
}
