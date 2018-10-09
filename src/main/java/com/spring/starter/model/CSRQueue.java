package com.spring.starter.model;

import javax.persistence.*;
import java.util.List;

public class CSRQueue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private int CSRQueueId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="customer_CSR_Id")
    @Column
    private Customer customer;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name="CSRQueueId")
    @Column
    private List<CustomerServiceRequest> customerServiceRequest;
    @Column
    private boolean complete;
    @Column
    private String comment;
    @Column
    private boolean backupStatus;

}
