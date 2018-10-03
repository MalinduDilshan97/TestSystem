package com.spring.starter.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class ServiceRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int serviceRequestId;

    @Column
    private String serviceRequestName;

    @Column
    private String digiFormType;

    @Column(unique = true, nullable = false)
    private int digiFormId;

    public ServiceRequest() {
        super();
    }


    public ServiceRequest(String serviceRequestName, String digiFormType, int digiFormId) {
        super();
        this.serviceRequestName = serviceRequestName;
        this.digiFormType = digiFormType;
        this.digiFormId = digiFormId;
    }


    public ServiceRequest(int serviceRequestId, String serviceRequestName, String digiFormType, int digiFormId) {
        super();
        this.serviceRequestId = serviceRequestId;
        this.serviceRequestName = serviceRequestName;
        this.digiFormType = digiFormType;
        this.digiFormId = digiFormId;
    }


    public int getServiceRequestId() {
        return serviceRequestId;
    }


    public void setServiceRequestId(int serviceRequestId) {
        this.serviceRequestId = serviceRequestId;
    }


    public String getServiceRequestName() {
        return serviceRequestName;
    }


    public void setServiceRequestName(String serviceRequestName) {
        this.serviceRequestName = serviceRequestName;
    }


    public String getDigiFormType() {
        return digiFormType;
    }


    public void setDigiFormType(String digiFormType) {
        this.digiFormType = digiFormType;
    }


    public int getDigiFormId() {
        return digiFormId;
    }


    public void setDigiFormId(int digiFormId) {
        this.digiFormId = digiFormId;
    }


}
