package com.spring.starter.model;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="withholding_fd_cd")
public class WithholdingFdCd {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int  withholdingFdId;
    private Date maturityDate;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "withholdingFdId")
    private List<FdCdNumbers> fdCdNumbers;


    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="csrId")
    private CustomerServiceRequest customerServiceRequest;

    public WithholdingFdCd() {
    }

    public int getWithholdingFdId() {
        return withholdingFdId;
    }

    public void setWithholdingFdId(int withholdingFdId) {
        this.withholdingFdId = withholdingFdId;
    }

    public Date getMaturityDate() {
        return maturityDate;
    }

    public void setMaturityDate(Date maturityDate) {
        this.maturityDate = maturityDate;
    }

    public List<FdCdNumbers> getFdCdNumbers() {
        return fdCdNumbers;
    }

    public void setFdCdNumbers(List<FdCdNumbers> fdCdNumbers) {
        this.fdCdNumbers = fdCdNumbers;
    }

    public CustomerServiceRequest getCustomerServiceRequest() {
        return customerServiceRequest;
    }

    public void setCustomerServiceRequest(CustomerServiceRequest customerServiceRequest) {
        this.customerServiceRequest = customerServiceRequest;
    }
}