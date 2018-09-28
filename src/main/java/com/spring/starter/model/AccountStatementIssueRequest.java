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
    private String NatureOfStatement;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customerServiceRequestId")
    private CustomerServiceRequest customerServiceRequest;

    public AccountStatementIssueRequest() {
    }

    public AccountStatementIssueRequest(int accountStatementIssueRequestId, long accountNo, Date fromDate, Date toDate, String natureOfStatement, CustomerServiceRequest customerServiceRequest) {
        this.accountStatementIssueRequestId = accountStatementIssueRequestId;
        this.accountNo = accountNo;
        this.fromDate = fromDate;
        this.toDate = toDate;
        NatureOfStatement = natureOfStatement;
        this.customerServiceRequest = customerServiceRequest;
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
        return NatureOfStatement;
    }

    public void setNatureOfStatement(String natureOfStatement) {
        NatureOfStatement = natureOfStatement;
    }

    public CustomerServiceRequest getCustomerServiceRequest() {
        return customerServiceRequest;
    }

    public void setCustomerServiceRequest(CustomerServiceRequest customerServiceRequest) {
        this.customerServiceRequest = customerServiceRequest;
    }
}
