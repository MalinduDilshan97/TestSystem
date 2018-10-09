package com.spring.starter.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Date;

@Entity
public class OtherFdCdRelatedRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int relatedReqId;
    private String request;
    private Date date;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="csrId")
    private CustomerServiceRequest customerServiceRequest;
    @NotNull
    @Pattern(regexp = "^(FD|CD)$", message = "Input must be 'FD' or 'CD'")
    private String accountType;


    public OtherFdCdRelatedRequest(String request, Date date, CustomerServiceRequest customerServiceRequest, @NotNull @Pattern(regexp = "^(FD|CD)$", message = "Input must be 'FD' or 'CD'") String accountType) {
        this.request = request;
        this.date = date;
        this.customerServiceRequest = customerServiceRequest;
        this.accountType = accountType;
    }

    public OtherFdCdRelatedRequest() {
    }

    public int getRelatedReqId() {
        return relatedReqId;
    }

    public void setRelatedReqId(int relatedReqId) {
        this.relatedReqId = relatedReqId;
    }

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public CustomerServiceRequest getCustomerServiceRequest() {
        return customerServiceRequest;
    }

    public void setCustomerServiceRequest(CustomerServiceRequest customerServiceRequest) {
        this.customerServiceRequest = customerServiceRequest;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }
}
