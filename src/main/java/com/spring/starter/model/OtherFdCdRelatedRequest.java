package com.spring.starter.model;

import javax.persistence.*;
import java.util.Date;

@Entity
public class OtherFdCdRelatedRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int relatedReqId;
    private String request;
    private Date date;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "csrId")
    private CustomerServiceRequest customerServiceRequest;


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
}
