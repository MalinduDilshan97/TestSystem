package com.spring.starter.DTO;

import javax.validation.constraints.NotNull;

public class IdentificationAddDTO {

    @NotNull
    private String Identification;

    @NotNull
    private int customerServiceRequestId;

    public IdentificationAddDTO() {
    }

    public IdentificationAddDTO(String identification, int customerServiceRequestId) {
        Identification = identification;
        this.customerServiceRequestId = customerServiceRequestId;
    }

    public String getIdentification() {
        return Identification;
    }

    public void setIdentification(String identification) {
        Identification = identification;
    }

    public int getCustomerServiceRequestId() {
        return customerServiceRequestId;
    }

    public void setCustomerServiceRequestId(int customerServiceRequestId) {
        this.customerServiceRequestId = customerServiceRequestId;
    }
}
