package com.spring.starter.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "service_request_tif")
public class ServiceRequestTif {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int serviceRequestTifId;

    private String url;

    private int queueId;

    private Date date;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customerServiceRequestId")
    private Customer customer;

    public ServiceRequestTif() {
    }

    public ServiceRequestTif(String url, int queueId, Date date, Customer customer) {
        this.url = url;
        this.queueId = queueId;
        this.date = date;
        this.customer = customer;
    }

    public int getServiceRequestTifId() {
        return serviceRequestTifId;
    }

    public void setServiceRequestTifId(int serviceRequestTifId) {
        this.serviceRequestTifId = serviceRequestTifId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getQueueId() {
        return queueId;
    }

    public void setQueueId(int queueId) {
        this.queueId = queueId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
