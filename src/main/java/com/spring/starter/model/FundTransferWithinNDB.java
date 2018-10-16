package com.spring.starter.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

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
    private String signatureUrl;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Branch_Id")
    private  Branch branch;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customerTransactionRequestId")
    private CustomerTransactionRequest customerTransactionRequest;
    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "fund_transfer_within_file_id")
    private List<FundTransferWithinNDBFile> fundTransferWithinNDBFiles;

    public FundTransferWithinNDB() {
    }

    public FundTransferWithinNDB(Date date, long fromAccount, String fromAccountType, String currency, double amount, long toAccount, String signatureUrl, Branch branch, CustomerTransactionRequest customerTransactionRequest, List<FundTransferWithinNDBFile> fundTransferWithinNDBFiles) {
        this.date = date;
        this.fromAccount = fromAccount;
        this.fromAccountType = fromAccountType;
        this.currency = currency;
        this.amount = amount;
        this.toAccount = toAccount;
        this.signatureUrl = signatureUrl;
        this.branch = branch;
        this.customerTransactionRequest = customerTransactionRequest;
        this.fundTransferWithinNDBFiles = fundTransferWithinNDBFiles;
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

    public String getSignatureUrl() {
        return signatureUrl;
    }

    public void setSignatureUrl(String signatureUrl) {
        this.signatureUrl = signatureUrl;
    }

    public Branch getBranch() {
        return branch;
    }

    public void setBranch(Branch branch) {
        this.branch = branch;
    }

    public CustomerTransactionRequest getCustomerTransactionRequest() {
        return customerTransactionRequest;
    }

    public void setCustomerTransactionRequest(CustomerTransactionRequest customerTransactionRequest) {
        this.customerTransactionRequest = customerTransactionRequest;
    }

    public List<FundTransferWithinNDBFile> getFundTransferWithinNDBFiles() {
        return fundTransferWithinNDBFiles;
    }

    public void setFundTransferWithinNDBFiles(List<FundTransferWithinNDBFile> fundTransferWithinNDBFiles) {
        this.fundTransferWithinNDBFiles = fundTransferWithinNDBFiles;
    }
}
