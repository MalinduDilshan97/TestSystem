package com.spring.starter.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "contact_details")
public class ContactDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int contactDetailsId;
    private String mobileNumber;
    private String residenceNumber;
    private String officeNumber;
    private String email;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "csrId")
    private CustomerServiceRequest customerServiceRequest;

    private boolean softReject = false;

    public boolean isSoftReject() {
        return softReject;
    }

    public void setSoftReject(boolean softReject) {
        this.softReject = softReject;
    }

    public ContactDetails() {
    }

    public ContactDetails(String mobileNumber, String residenceNumber, String officeNumber, String email, CustomerServiceRequest customerServiceRequest) {
        this.mobileNumber = mobileNumber;
        this.residenceNumber = residenceNumber;
        this.officeNumber = officeNumber;
        this.email = email;
        this.customerServiceRequest = customerServiceRequest;
    }

    public ContactDetails(int contactDetailsId, String mobileNumber, String residenceNumber, String officeNumber, String email, CustomerServiceRequest customerServiceRequest) {
        this.contactDetailsId = contactDetailsId;
        this.mobileNumber = mobileNumber;
        this.residenceNumber = residenceNumber;
        this.officeNumber = officeNumber;
        this.email = email;
        this.customerServiceRequest = customerServiceRequest;
    }

    public int getContactDetailsId() {
        return contactDetailsId;
    }

    public void setContactDetailsId(int contactDetailsId) {
        this.contactDetailsId = contactDetailsId;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getResidenceNumber() {
        return residenceNumber;
    }

    public void setResidenceNumber(String residenceNumber) {
        this.residenceNumber = residenceNumber;
    }

    public String getOfficeNumber() {
        return officeNumber;
    }

    public void setOfficeNumber(String officeNumber) {
        this.officeNumber = officeNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public CustomerServiceRequest getCustomerServiceRequest() {
        return customerServiceRequest;
    }

    public void setCustomerServiceRequest(CustomerServiceRequest customerServiceRequest) {
        this.customerServiceRequest = customerServiceRequest;
    }
}
