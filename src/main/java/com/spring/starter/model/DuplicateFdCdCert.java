package com.spring.starter.model;

import javax.persistence.*;
import java.util.Date;

@Entity
public class DuplicateFdCdCert {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int duplicateId;

    private String fdCdDepositNo;

    private Date date;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "csrId")
    private CustomerServiceRequest customerServiceRequest;


    public DuplicateFdCdCert() {
    }


    public String getFdCdDepositNo() {
        return fdCdDepositNo;
    }

    public void setFdCdDepositNo(String fdCdDepositNo) {
        this.fdCdDepositNo = fdCdDepositNo;
    }

    public int getDuplicateId() {
        return duplicateId;
    }

    public void setDuplicateId(int duplicateId) {
        this.duplicateId = duplicateId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public CustomerServiceRequest getCustomerServiceRequest() {
        return customerServiceRequest;
    }

    public void setCustomerServiceRequest(CustomerServiceRequest customerServiceRequest) {
        this.customerServiceRequest = customerServiceRequest;
    }
}
