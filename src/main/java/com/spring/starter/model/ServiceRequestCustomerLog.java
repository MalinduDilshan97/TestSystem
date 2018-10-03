package com.spring.starter.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="service_request_customer_log")
public class ServiceRequestCustomerLog {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int serviceRequestCustomerLogId;
    private Date date;
    private String identification;
    private String ip;
    private String message;

    public ServiceRequestCustomerLog() {
    }

    public ServiceRequestCustomerLog(Date date, String identification, String ip, String message) {
        this.date = date;
        this.identification = identification;
        this.ip = ip;
        this.message = message;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getServiceRequestCustomerLogId() {
        return serviceRequestCustomerLogId;
    }

    public void setServiceRequestCustomerLogId(int serviceRequestCustomerLogId) {
        this.serviceRequestCustomerLogId = serviceRequestCustomerLogId;
    }

    public String getIdentification() {
        return identification;
    }

    public void setIdentification(String identification) {
        this.identification = identification;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
