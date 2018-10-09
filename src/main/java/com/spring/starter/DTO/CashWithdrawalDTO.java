package com.spring.starter.DTO;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

public class CashWithdrawalDTO implements Serializable {

    @NotNull
    private Date date;
    @NotNull
    @Min(2)
    private long accountNo;
    @NotNull
    @Size(min = 2)
    @Pattern(regexp = "^([A-Za-z0-9.\\s])*$")
    private String accountHolder;
    @NotNull
    private String currency;
    @NotNull
    private double amount;
    @NotNull
    @Size(min = 2)
    @Pattern(regexp = "^([A-Za-z0-9\\s])*$")
    private String amountInWords;
    @NotNull
    @Size(min = 2)
    @Pattern(regexp = "^([A-Za-z0-9])*$")
    private String identity;
    @NotNull
    private  int ndbBranchId;

    public CashWithdrawalDTO() {
    }

    public CashWithdrawalDTO(Date date, long accountNo, String accountHolder, String currency, double amount, String amountInWords, String identity, int ndbBranchId) {
        this.date = date;
        this.accountNo = accountNo;
        this.accountHolder = accountHolder;
        this.currency = currency;
        this.amount = amount;
        this.amountInWords = amountInWords;
        this.identity = identity;
        this.ndbBranchId = ndbBranchId;
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

    public int getNdbBranchId() {
        return ndbBranchId;
    }

    public void setNdbBranchId(int ndbBranchId) {
        this.ndbBranchId = ndbBranchId;
    }
}
