package com.spring.starter.DTO;

import java.io.Serializable;

public class ContactDetailsDTO implements Serializable {

    private int contactDetailsId;
    private String mobileNumber;
    private String residenceNumber;
    private String officeNumber;
    private String email;
    private int customerServiceRequestId;

    public ContactDetailsDTO() {
    }

    public ContactDetailsDTO(String mobileNumber, String residenceNumber, String officeNumber, String email, int customerServiceRequestId) {
        this.mobileNumber = mobileNumber;
        this.residenceNumber = residenceNumber;
        this.officeNumber = officeNumber;
        this.email = email;
        this.customerServiceRequestId = customerServiceRequestId;
    }

    public ContactDetailsDTO(int contactDetailsId, String mobileNumber, String residenceNumber, String officeNumber, String email, int customerServiceRequestId) {
        this.contactDetailsId = contactDetailsId;
        this.mobileNumber = mobileNumber;
        this.residenceNumber = residenceNumber;
        this.officeNumber = officeNumber;
        this.email = email;
        this.customerServiceRequestId = customerServiceRequestId;
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

    public int getCustomerServiceRequestId() {
        return customerServiceRequestId;
    }

    public void setCustomerServiceRequestId(int customerServiceRequestId) {
        this.customerServiceRequestId = customerServiceRequestId;
    }
}
