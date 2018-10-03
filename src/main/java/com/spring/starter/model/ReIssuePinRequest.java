package com.spring.starter.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "reissue_pin_request")
public class ReIssuePinRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int reIssuePinRequestId;
    private String Branch;
    private String Address;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customerServiceRequestId")
    private CustomerServiceRequest customerServiceRequest;

    public ReIssuePinRequest() {
    }

    public ReIssuePinRequest(int reIssuePinRequestId, String branch, String address, CustomerServiceRequest customerServiceRequest) {
        this.reIssuePinRequestId = reIssuePinRequestId;
        Branch = branch;
        Address = address;
        this.customerServiceRequest = customerServiceRequest;
    }

    public int getReIssuePinRequestId() {
        return reIssuePinRequestId;
    }

    public void setReIssuePinRequestId(int reIssuePinRequestId) {
        this.reIssuePinRequestId = reIssuePinRequestId;
    }

    public String getBranch() {
        return Branch;
    }

    public void setBranch(String branch) {
        Branch = branch;
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

    public void setCustomerServiceRequest(CustomerServiceRequest customerServiceRequest) {
        this.customerServiceRequest = customerServiceRequest;
    }
}
