package com.spring.starter.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "cash_withdrawal")
public class CashWithdrawal implements Serializable {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private int cashWithdrawalId;
    private Date  date;
    private long accountNo;
    private String accountHolder;
    private String currency;
    private double amount;
    private String amountInWords;
    private String identity;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BranchId")
    private  NDBBranch ndbBranch;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customerTransactionRequestId")
    private CustomerTransactionRequest customerTransactionRequest;

    public CashWithdrawal() {
    }

    public CashWithdrawal(Date date, long accountNo, String accountHolder, String currency, double amount, String amountInWords, String identity, NDBBranch ndbBranch, CustomerTransactionRequest customerTransactionRequest) {
        this.date = date;
        this.accountNo = accountNo;
        this.accountHolder = accountHolder;
        this.currency = currency;
        this.amount = amount;
        this.amountInWords = amountInWords;
        this.identity = identity;
        this.ndbBranch = ndbBranch;
        this.customerTransactionRequest = customerTransactionRequest;
    }

    public int getCashWithdrawalId() {
        return cashWithdrawalId;
    }

    public void setCashWithdrawalId(int cashWithdrawalId) {
        this.cashWithdrawalId = cashWithdrawalId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public long getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(long accountNo) {
        this.accountNo = accountNo;
    }

    public String getAccountHolder() {
        return accountHolder;
    }

    public void setAccountHolder(String accountHolder) {
        this.accountHolder = accountHolder;
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

    public String getAmountInWords() {
        return amountInWords;
    }

    public void setAmountInWords(String amountInWords) {
        this.amountInWords = amountInWords;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
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
