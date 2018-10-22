package com.spring.starter.model;


import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "CustomerServiceRequest")
public class CustomerServiceRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int customerServiceRequestId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "serviceRequestId")
    private ServiceRequest serviceRequest;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customerId")
    @JsonIgnore
    private Customer customer;
    private boolean status = false;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "staffUserId")
    private List<StaffUser> staffUser;
    private Date requestDate;
    private Date requestCompleteDate;
    private String url;
    private boolean softReject = false;

    public CustomerServiceRequest() {

    }

    public int getCustomerServiceRequestId() {
        return customerServiceRequestId;
    }

    public void setCustomerServiceRequestId(int customerServiceRequestId) {
        this.customerServiceRequestId = customerServiceRequestId;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public ServiceRequest getServiceRequest() {
        return serviceRequest;
    }

    public void setServiceRequest(ServiceRequest serviceRequest) {
        this.serviceRequest = serviceRequest;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public List<StaffUser> getStaffUser() {
        return staffUser;
    }

    public void setStaffUser(List<StaffUser> staffUser) {
        this.staffUser = staffUser;
    }

    public Date getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(Date requestDate) {
        this.requestDate = requestDate;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Date getRequestCompleteDate() {
        return requestCompleteDate;
    }

    public boolean isSoftReject() {
        return softReject;
    }

    public void setSoftReject(boolean softReject) {
        this.softReject = softReject;
    }

    public void setRequestCompleteDate(Date requestCompleteDate) {
        this.requestCompleteDate = requestCompleteDate;
    }
}
