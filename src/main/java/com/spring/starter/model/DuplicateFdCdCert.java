package com.spring.starter.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Date;

@Entity
public class DuplicateFdCdCert {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int duplicateId;

    @NotNull
    private String fdCdDepositNo;

    private Date date;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="csrId")
    private CustomerServiceRequest customerServiceRequest;

    @NotNull
    @Pattern(regexp = "^(FD|CD)$", message = "Input must be 'FD' or 'CD'")
    private String accountType;

    public DuplicateFdCdCert(@NotNull String fdCdDepositNo, Date date, CustomerServiceRequest customerServiceRequest, @NotNull @Pattern(regexp = "^(FD|CD)$", message = "Input must be 'FD' or 'CD'") String accountType) {
        this.fdCdDepositNo = fdCdDepositNo;
        this.date = date;
        this.customerServiceRequest = customerServiceRequest;
        this.accountType = accountType;
    }

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

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }
}
