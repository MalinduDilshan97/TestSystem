package com.spring.starter.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "fund_transfer_within_ndb")
public class FundTransferWithinNDB implements Serializable {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private int fundTransferWithinNdbId;
    private Date date;
    private long fromAccount;
    private String fromAccountType;
    private String currency;
    private double amount;
    private long toAccount;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BranchId")
    private  NDBBranch ndbBranch;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customerTransactionRequestId")
    private CustomerTransactionRequest customerTransactionRequest;

    public FundTransferWithinNDB() {
    }

    public FundTransferWithinNDB(int fundTransferWithinNdbId, Date date, long fromAccount, String fromAccountType, String currency, double amount, long toAccount, NDBBranch ndbBranch, CustomerTransactionRequest customerTransactionRequest) {
        this.fundTransferWithinNdbId = fundTransferWithinNdbId;
        this.date = date;
        this.fromAccount = fromAccount;
        this.fromAccountType = fromAccountType;
        this.currency = currency;
        this.amount = amount;
        this.toAccount = toAccount;
        this.ndbBranch = ndbBranch;
        this.customerTransactionRequest = customerTransactionRequest;
    }

    public int getFundTransferWithinNdbId() {
        return fundTransferWithinNdbId;
    }

    public void setFundTransferWithinNdbId(int fundTransferWithinNdbId) {
        this.fundTransferWithinNdbId = fundTransferWithinNdbId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public long getFromAccount() {
        return fromAccount;
    }

    public void setFromAccount(long fromAccount) {
        this.fromAccount = fromAccount;
    }

    public String getFromAccountType() {
        return fromAccountType;
    }

    public void setFromAccountType(String fromAccountType) {
        this.fromAccountType = fromAccountType;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public long getToAccount() {
        return toAccount;
    }

    public void setToAccount(long toAccount) {
        this.toAccount = toAccount;
    }

    public NDBBranch getNdbBranch() {
        return ndbBranch;
    }

    public void setNdbBranch(NDBBranch ndbBranch) {
        this.ndbBranch = ndbBranch;
    }

    public CustomerTransactionRequest getCustomerTransactionRequest() {
        return customerTransactionRequest;
    }

    public void setCustomerTransactionRequest(CustomerTransactionRequest customerTransactionRequest) {
        this.customerTransactionRequest = customerTransactionRequest;
    }
}