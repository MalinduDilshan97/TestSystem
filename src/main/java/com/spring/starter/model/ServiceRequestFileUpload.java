package com.spring.starter.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name = "serviceRequestFileUpload")
public class ServiceRequestFileUpload {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int serviceRequestFileUploadId;

    private String fileType;

    private String fileUrl;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="customerId")
    @JsonIgnore
    private Customer customer;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="customerServiceRequest")
    @JsonIgnore
    private CustomerServiceRequest customerServiceRequest;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="bankServiceRequest")
    @JsonIgnore
    private ServiceRequest bankserviceRequest;

    public ServiceRequestFileUpload() {
    }

    public ServiceRequestFileUpload(String fileType, String fileUrl, Customer customer,
                                    CustomerServiceRequest customerServiceRequest, ServiceRequest bankserviceRequest) {
        this.fileType = fileType;
        this.fileUrl = fileUrl;
        this.customer = customer;
        this.customerServiceRequest = customerServiceRequest;
        this.bankserviceRequest = bankserviceRequest;
    }

    public int getServiceRequestFileUploadId() {
        return serviceRequestFileUploadId;
    }

    public void setServiceRequestFileUploadId(int serviceRequestFileUploadId) {
        this.serviceRequestFileUploadId = serviceRequestFileUploadId;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public CustomerServiceRequest getCustomerServiceRequest() {
        return customerServiceRequest;
    }

    public void setCustomerServiceRequest(CustomerServiceRequest customerServiceRequest) {
        this.customerServiceRequest = customerServiceRequest;
    }

    public ServiceRequest getBankserviceRequest() {
        return bankserviceRequest;
    }

    public void setBankserviceRequest(ServiceRequest bankserviceRequest) {
        this.bankserviceRequest = bankserviceRequest;
    }
}
