package com.spring.starter.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "service_request_form_log")
public class ServiceRequestFormLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int serviceRequestFormLogId;
    private int digiFormId;
    private int customerId;
    private Date date;
    private int fromId;
    private String ip;
    private boolean status;
    private String message;

    public ServiceRequestFormLog() {
    }

    public ServiceRequestFormLog(int digiFormId, int customerId, Date date, int fromId, String ip, boolean status, String message) {
        this.digiFormId = digiFormId;
        this.customerId = customerId;
        this.date = date;
        this.fromId = fromId;
        this.ip = ip;
        this.status = status;
        this.message = message;
    }

    public int getServiceRequestFormLogId() {
        return serviceRequestFormLogId;
    }

    public void setServiceRequestFormLogId(int serviceRequestFormLogId) {
        this.serviceRequestFormLogId = serviceRequestFormLogId;
    }

    public int getDigiFormId() {
        return digiFormId;
    }

    public void setDigiFormId(int digiFormId) {
        this.digiFormId = digiFormId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getFromId() {
        return fromId;
    }

    public void setFromId(int fromId) {
        this.fromId = fromId;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
