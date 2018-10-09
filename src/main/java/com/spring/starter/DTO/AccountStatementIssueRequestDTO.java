package com.spring.starter.DTO;

import java.io.Serializable;

public class AccountStatementIssueRequestDTO implements Serializable {

    private int accountStatementIssueRequestId;
    private long accountNo;
    private String fromDate;
    private String toDate;
    private int natureOfStatement;
    private int customerServiceRequestId;

    public AccountStatementIssueRequestDTO() {
    }

    public AccountStatementIssueRequestDTO(int accountStatementIssueRequestId, long accountNo, String fromDate,
                                           String toDate, int natureOfStatement, int customerServiceRequestId) {
        this.accountStatementIssueRequestId = accountStatementIssueRequestId;
        this.accountNo = accountNo;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.natureOfStatement = natureOfStatement;
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

    public int getNatureOfStatement() {
        return natureOfStatement;
    }

    public void setNatureOfStatement(int natureOfStatement) {
        this.natureOfStatement = natureOfStatement;
    }

    public int getCustomerServiceRequestId() {
        return customerServiceRequestId;
    }

    public void setCustomerServiceRequestId(int customerServiceRequestId) {
        this.customerServiceRequestId = customerServiceRequestId;
    }
}
