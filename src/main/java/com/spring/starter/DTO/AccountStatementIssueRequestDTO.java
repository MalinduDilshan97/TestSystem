package com.spring.starter.DTO;

import java.io.Serializable;

public class AccountStatementIssueRequestDTO implements Serializable {

    private int accountStatementIssueRequestId;
    private long accountNo;
    private String fromDate;
    private String toDate;
    private String NatureOfStatement;
    private int customerServiceRequestId;

    public AccountStatementIssueRequestDTO() {
    }

    public AccountStatementIssueRequestDTO(long accountNo, String fromDate, String toDate, String natureOfStatement, int customerServiceRequestId) {
        this.accountNo = accountNo;
        this.fromDate = fromDate;
        this.toDate = toDate;
        NatureOfStatement = natureOfStatement;
        this.customerServiceRequestId = customerServiceRequestId;
    }

    public AccountStatementIssueRequestDTO(int accountStatementIssueRequestId, long accountNo, String fromDate, String toDate, String natureOfStatement, int customerServiceRequestId) {
        this.accountStatementIssueRequestId = accountStatementIssueRequestId;
        this.accountNo = accountNo;
        this.fromDate = fromDate;
        this.toDate = toDate;
        NatureOfStatement = natureOfStatement;
        this.customerServiceRequestId = customerServiceRequestId;
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

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    public String getNatureOfStatement() {
        return NatureOfStatement;
    }

    public void setNatureOfStatement(String natureOfStatement) {
        NatureOfStatement = natureOfStatement;
    }

    public int getCustomerServiceRequestId() {
        return customerServiceRequestId;
    }

    public void setCustomerServiceRequestId(int customerServiceRequestId) {
        this.customerServiceRequestId = customerServiceRequestId;
    }
}
