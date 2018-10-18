package com.spring.starter.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "reissue_pin_request")
public class ReIssuePinRequest  {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private int reIssuePinRequestId;
    private boolean isBranch = false;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "branchId")
    private Branch branch;
    private boolean isAddress= false;
    private String Address;
    private boolean isCurrespondingAddress= false;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customerServiceRequestId")
    private CustomerServiceRequest customerServiceRequest;

    public ReIssuePinRequest() {
    }

    public Branch getBranch() {
        return branch;
    }

    public void setBranch(Branch branch) {
        this.branch = branch;
    }

    public ReIssuePinRequest(boolean isBranch, Branch branch, boolean isAddress, String address, boolean isCurrespondingAddress, CustomerServiceRequest customerServiceRequest) {
        this.isBranch = isBranch;
        this.branch = branch;
        this.isAddress = isAddress;
        Address = address;
        this.isCurrespondingAddress = isCurrespondingAddress;
        this.customerServiceRequest = customerServiceRequest;
    }

    public int getReIssuePinRequestId() {
        return reIssuePinRequestId;
    }

    public void setReIssuePinRequestId(int reIssuePinRequestId) {
        this.reIssuePinRequestId = reIssuePinRequestId;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public CustomerServiceRequest getCustomerServiceRequest() {
        return customerServiceRequest;
    }

    public boolean isBranch() {
        return isBranch;
    }

    public void setBranch(boolean branch) {
        isBranch = branch;
    }

    public boolean isAddress() {
        return isAddress;
    }

    public void setAddress(boolean address) {
        isAddress = address;
    }

    public boolean isCurrespondingAddress() {
        return isCurrespondingAddress;
    }

    public void setCurrespondingAddress(boolean currespondingAddress) {
        isCurrespondingAddress = currespondingAddress;
    }

    public void setCustomerServiceRequest(CustomerServiceRequest customerServiceRequest) {
        this.customerServiceRequest = customerServiceRequest;
    }
}
