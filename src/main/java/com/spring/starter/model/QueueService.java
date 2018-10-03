package com.spring.starter.model;


import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class QueueService {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int queueServiceId;

    @OneToOne
    @JoinColumn(name = "queue_id")
    private QueueNum queue;

    @OneToOne
    @JoinColumn(name = "teller_id")
    private StaffUser tellerId;

    @ManyToMany
    @JoinColumn(name = "service_request_ids")
    private List<ServiceRequest> serviceRequest;

    public QueueService() {
        super();
    }

    public QueueService(int queueServiceId, QueueNum queue, StaffUser tellerId, List<ServiceRequest> serviceRequest) {
        super();
        this.queueServiceId = queueServiceId;
        this.queue = queue;
        this.tellerId = tellerId;
        this.serviceRequest = serviceRequest;
    }

    public int getQueueServiceId() {
        return queueServiceId;
    }

    public void setQueueServiceId(int queueServiceId) {
        this.queueServiceId = queueServiceId;
    }

    public QueueNum getQueue() {
        return queue;
    }

    public void setQueue(QueueNum queue) {
        this.queue = queue;
    }

    public StaffUser getTellerId() {
        return tellerId;
    }

    public void setTellerId(StaffUser tellerId) {
        this.tellerId = tellerId;
    }

    public List<ServiceRequest> getServiceRequest() {
        return serviceRequest;
    }

    public void setServiceRequest(List<ServiceRequest> serviceRequest) {
        this.serviceRequest = serviceRequest;
    }

}
