package com.spring.starter.DTO;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class CustomerTransactionDTO {

    @NotNull
    @NotEmpty
    int customerId;
    @NotNull
    @NotEmpty
    int transactionRequestId;

    public CustomerTransactionDTO() {
    }

    public CustomerTransactionDTO(int customerId, int transactionRequestId) {
        this.customerId = customerId;
        this.transactionRequestId = transactionRequestId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getTransactionRequestId() {
        return transactionRequestId;
    }

    public void setTransactionRequestId(int transactionRequestId) {
        this.transactionRequestId = transactionRequestId;
    }
}
