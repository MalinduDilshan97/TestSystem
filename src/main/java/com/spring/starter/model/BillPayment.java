package com.spring.starter.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
@Table(name = "bill_payment")
public class BillPayment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int billPaymentId;
    @NotNull
    @Size(min = 2)
    @Pattern(regexp = "^([A-Za-z0-9_\\s])*$")
    private String accountName;
    @NotNull
    @NotNull
    @Size(min = 2)
    @Pattern(regexp = "^([A-Za-z0-9_\\s])*$")
    private String benificiaryName;
    @NotNull
    @Size(min = 9 , max = 10)
    @Pattern(regexp = "^([+0-9])*$")
    private String benificiaryTelNo;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "branchId")
    @NotNull
    private Branch branch;

    @FutureOrPresent
    private Date date;

    private boolean currencyIsCash = false;

    private boolean currencyIsChaque = false;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "currencyId")
    private Currency currency;

    @NotNull
    private String collectionAccountNo;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "billPaymentReferanceId")
    private BillPaymentReferance billPaymentReferance;

    private int valueOf5000Notes;

    private int valueOf2000Notes;

    private int valueof1000Notes;

    private int valueOf500Notes;

    private int valueOf100Notes;

    private int valueOf50Notes;

    private int valueOf20Notes;

    private int valueOf10Notes;

    private double valueOfcoins;

    @NotNull
    private double total;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customerTransactionRequestId")
    private CustomerTransactionRequest customerTransactionRequest;

    public BillPayment() {
    }

    public int getBillPaymentId() {
        return billPaymentId;
    }

    public void setBillPaymentId(int billPaymentId) {
        this.billPaymentId = billPaymentId;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getBenificiaryName() {
        return benificiaryName;
    }

    public void setBenificiaryName(String benificiaryName) {
        this.benificiaryName = benificiaryName;
    }

    public String getBenificiaryTelNo() {
        return benificiaryTelNo;
    }

    public void setBenificiaryTelNo(String benificiaryTelNo) {
        this.benificiaryTelNo = benificiaryTelNo;
    }

    public Branch getBranch() {
        return branch;
    }

    public void setBranch(Branch branch) {
        this.branch = branch;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public boolean isCurrencyIsCash() {
        return currencyIsCash;
    }

    public void setCurrencyIsCash(boolean currencyIsCash) {
        this.currencyIsCash = currencyIsCash;
    }

    public boolean isCurrencyIsChaque() {
        return currencyIsChaque;
    }

    public void setCurrencyIsChaque(boolean currencyIsChaque) {
        this.currencyIsChaque = currencyIsChaque;
    }

    public String getCollectionAccountNo() {
        return collectionAccountNo;
    }

    public void setCollectionAccountNo(String collectionAccountNo) {
        this.collectionAccountNo = collectionAccountNo;
    }

    public int getValueOf5000Notes() {
        return valueOf5000Notes;
    }

    public void setValueOf5000Notes(int valueOf5000Notes) {
        this.valueOf5000Notes = valueOf5000Notes;
    }

    public int getValueOf2000Notes() {
        return valueOf2000Notes;
    }

    public void setValueOf2000Notes(int valueOf2000Notes) {
        this.valueOf2000Notes = valueOf2000Notes;
    }

    public int getValueof1000Notes() {
        return valueof1000Notes;
    }

    public void setValueof1000Notes(int valueof1000Notes) {
        this.valueof1000Notes = valueof1000Notes;
    }

    public int getValueOf500Notes() {
        return valueOf500Notes;
    }

    public void setValueOf500Notes(int valueOf500Notes) {
        this.valueOf500Notes = valueOf500Notes;
    }

    public int getValueOf100Notes() {
        return valueOf100Notes;
    }

    public void setValueOf100Notes(int valueOf100Notes) {
        this.valueOf100Notes = valueOf100Notes;
    }

    public int getValueOf50Notes() {
        return valueOf50Notes;
    }

    public void setValueOf50Notes(int valueOf50Notes) {
        this.valueOf50Notes = valueOf50Notes;
    }

    public int getValueOf20Notes() {
        return valueOf20Notes;
    }

    public void setValueOf20Notes(int valueOf20Notes) {
        this.valueOf20Notes = valueOf20Notes;
    }

    public int getValueOf10Notes() {
        return valueOf10Notes;
    }

    public void setValueOf10Notes(int valueOf10Notes) {
        this.valueOf10Notes = valueOf10Notes;
    }

    public double getValueOfcoins() {
        return valueOfcoins;
    }

    public void setValueOfcoins(double valueOfcoins) {
        this.valueOfcoins = valueOfcoins;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public CustomerTransactionRequest getCustomerTransactionRequest() {
        return customerTransactionRequest;
    }

    public void setCustomerTransactionRequest(CustomerTransactionRequest customerTransactionRequest) {
        this.customerTransactionRequest = customerTransactionRequest;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public BillPaymentReferance getBillPaymentReferance() {
        return billPaymentReferance;
    }

    public void setBillPaymentReferance(BillPaymentReferance billPaymentReferance) {
        this.billPaymentReferance = billPaymentReferance;
    }
}
