package com.spring.starter.DTO;

import java.io.Serializable;

public class ReIssuePinRequestDTO implements Serializable {

    private int reIssuePinRequestId;
    private String branch;
    private String address;
    private int customerServiceRequestId;

    public ReIssuePinRequestDTO() {
    }

    public ReIssuePinRequestDTO(String branch, String address, int customerServiceRequestId) {
        this.branch = branch;
        this.address = address;
        this.customerServiceRequestId = customerServiceRequestId;
    }

    public ReIssuePinRequestDTO(int reIssuePinRequestId, String branch, String address, int customerServiceRequestId) {
        this.reIssuePinRequestId = reIssuePinRequestId;
        this.branch = branch;
        this.address = address;
        this.customerServiceRequestId = customerServiceRequestId;
    }

    public int getReIssuePinRequestId() {
        return reIssuePinRequestId;
    }

    public void setReIssuePinRequestId(int reIssuePinRequestId) {
        this.reIssuePinRequestId = reIssuePinRequestId;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getCustomerServiceRequestId() {
        return customerServiceRequestId;
    }

    public void setCustomerServiceRequestId(int customerServiceRequestId) {
        this.customerServiceRequestId = customerServiceRequestId;
    }
}