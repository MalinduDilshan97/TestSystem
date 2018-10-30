package com.spring.starter.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "AccountStatementIssueRequest")
public class AccountStatementIssueRequest implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int accountStatementIssueRequestId;
    private long accountNo;
    private Date fromDate;
    private Date toDate;
    private String natureOfStatement;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customerServiceRequestId")
    private CustomerServiceRequest customerServiceRequest;

    private boolean softReject = false;

    public boolean isSoftReject() {
        return softReject;
    }

    public void setSoftReject(boolean softReject) {
        this.softReject = softReject;
    }

    public AccountStatementIssueRequest() {
    }

    public AccountStatementIssueRequest(long accountNo, Date fromDate, Date toDate, String natureOfStatement, CustomerServiceRequest customerServiceRequest, boolean softReject) {
        this.accountNo = accountNo;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.natureOfStatement = natureOfStatement;
        this.customerServiceRequest = customerServiceRequest;
        this.softReject = softReject;
    }

    public int getAccountStatementIssueRequestId() {
        return accountStatementIssueRequestId;
    }

    public void setAccountStatementIssueRequestId(int accountStatementIssueRequestId) {
        this.accountStatementIssueRequestId = accountStatementIssueRequestId;
    }

    public long getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(long accountNo) {
        this.accountNo = accountNo;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    public String getNatureOfStatement() {
        return natureOfStatement;
    }

    public void setNatureOfStatement(String natureOfStatement) {
        this.natureOfStatement = natureOfStatement;
    }

    public CustomerServiceRequest getCustomerServiceRequest() {
        return customerServiceRequest;
    }

    public void setCustomerServiceRequest(CustomerServiceRequest customerServiceRequest) {
        this.customerServiceRequest = customerServiceRequest;
    }
}
