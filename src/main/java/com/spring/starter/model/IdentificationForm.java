package com.spring.starter.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "identification_form")
public class IdentificationForm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ChangeIdentificationFormId;

    @NotNull
    private String identification;

    private String documentUrl;

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

    public IdentificationForm() {
        super();
    }

    public IdentificationForm(@NotNull String identification, String documentUrl, CustomerServiceRequest customerServiceRequest) {
        this.identification = identification;
        this.documentUrl = documentUrl;
        this.customerServiceRequest = customerServiceRequest;
    }

    public IdentificationForm(int changeIdentificationFormId, @NotNull String identification, String documentUrl, CustomerServiceRequest customerServiceRequest) {
        ChangeIdentificationFormId = changeIdentificationFormId;
        this.identification = identification;
        this.documentUrl = documentUrl;
        this.customerServiceRequest = customerServiceRequest;
    }

    public int getChangeIdentificationFormId() {
        return ChangeIdentificationFormId;
    }

    public void setChangeIdentificationFormId(int changeIdentificationFormId) {
        ChangeIdentificationFormId = changeIdentificationFormId;
    }

    public String getIdentification() {
        return identification;
    }

    public void setIdentification(String identification) {
        this.identification = identification;
    }

    public String getDocumentUrl() {
        return documentUrl;
    }

    public void setDocumentUrl(String documentUrl) {
        this.documentUrl = documentUrl;
    }

    public CustomerServiceRequest getCustomerServiceRequest() {
        return customerServiceRequest;
    }

    public void setCustomerServiceRequest(CustomerServiceRequest customerServiceRequest) {
        this.customerServiceRequest = customerServiceRequest;
    }

}
