package com.spring.starter.DTO;

import java.io.Serializable;

public class DuplicatePassBookRequestDTO implements Serializable {

    private int DuplicatePassBookRequestId;
    private long AccountNumber;
    private int customerServiceRequestId;

    public DuplicatePassBookRequestDTO() {
    }

    public DuplicatePassBookRequestDTO(long accountNumber, int customerServiceRequestId) {
        AccountNumber = accountNumber;
        this.customerServiceRequestId = customerServiceRequestId;
    }

    public DuplicatePassBookRequestDTO(int duplicatePassBookRequestId, long accountNumber, int customerServiceRequestId) {
        DuplicatePassBookRequestId = duplicatePassBookRequestId;
        AccountNumber = accountNumber;
        this.customerServiceRequestId = customerServiceRequestId;
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

    public int getCustomerServiceRequestId() {
        return customerServiceRequestId;
    }

    public void setCustomerServiceRequestId(int customerServiceRequestId) {
        this.customerServiceRequestId = customerServiceRequestId;
    }
}
