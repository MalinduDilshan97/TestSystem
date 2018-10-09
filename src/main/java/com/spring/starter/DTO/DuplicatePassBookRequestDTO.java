package com.spring.starter.DTO;

import java.io.Serializable;

public class DuplicatePassBookRequestDTO implements Serializable {

    private int duplicatePassBookRequestId;
    private long accountNumber;
    private int customerServiceRequestId;

    public DuplicatePassBookRequestDTO() {
    }

    public DuplicatePassBookRequestDTO(int duplicatePassBookRequestId, long accountNumber, int customerServiceRequestId) {
        this.duplicatePassBookRequestId = duplicatePassBookRequestId;
        this.accountNumber = accountNumber;
        this.customerServiceRequestId = customerServiceRequestId;
    }

    public int getDuplicatePassBookRequestId() {
        return duplicatePassBookRequestId;
    }

    public void setDuplicatePassBookRequestId(int duplicatePassBookRequestId) {
        this.duplicatePassBookRequestId = duplicatePassBookRequestId;
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
