package com.spring.starter.model;

import javax.persistence.*;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "cash_deposit")
public class CashDeposit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int cashDepositId;

    @NotNull
    @NotNull
    @Size(min = 2)
    @Pattern(regexp = "^([A-Za-z0-9_\\s])*$")
    private String accountHolderName;

    @NotNull
    @Size(min = 6)
    @Pattern(regexp = "^[0-9]*$")
    private String accountNumber;

    @NotNull
    @NotNull
    @Size(min = 2)
    @Pattern(regexp = "^([A-Za-z0-9_\\s])*$")
    private String nameOfDepositor;

    @NotNull
    @Size(min = 2)
    @Pattern(regexp = "^([A-Za-z.,\\s])*$")
    private String address;

    @NotNull
    private String identification;

    @NotNull
    @Size(min = 5)
    @Pattern(regexp = "^([A-Za-z.,\\s])*$")
    private String purposeOfDeposit;

    @NotNull
    @Size(min = 5)
    @Pattern(regexp = "^([A-Za-z.,\\s])*$")
    private String ammountInWords;

    @NotNull
    @Pattern(regexp = "^([A-Za-z0-9_\\s])*$")
    private String phoneNumberAndExtn;

    @FutureOrPresent
    private Date date;

    @NotNull
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "currencyId")
    private Currency currency;

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

    private String signatureUrl;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customerTransactionRequestId")
    private CustomerTransactionRequest customerTransactionRequest;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "cash_deposit_relation_file_id")
    private List<CashDepositFile> cashDepositFiles;

    private boolean status = false;

    public CashDeposit() {
    }

    public CashDeposit(@NotNull @NotNull @Size(min = 2) @Pattern(regexp = "^([A-Za-z0-9_\\s])*$")
                               String accountHolderName, @NotNull @Size(min = 6) @Pattern(regexp = "^[0-9]*$")
            String accountNumber, @NotNull @NotNull @Size(min = 2) @Pattern(regexp = "^([A-Za-z0-9_\\s])*$") String nameOfDepositor, @NotNull @Size(min = 2) @Pattern(regexp = "^([A-Za-z.,\\s])*$") String address, @NotNull String identification,
                       @NotNull @Size(min = 5) @Pattern(regexp = "^([A-Za-z.,\\s])*$") String purposeOfDeposit,
                       @NotNull @Size(min = 5) @Pattern(regexp = "^([A-Za-z.,\\s])*$") String ammountInWords, @NotNull @Pattern(regexp = "^([A-Za-z0-9_\\s])*$") String phoneNumberAndExtn, @FutureOrPresent Date date, @NotNull Currency currency, int valueOf5000Notes, int valueOf2000Notes, int valueof1000Notes, int valueOf500Notes, int valueOf100Notes, int valueOf50Notes, int valueOf20Notes, int valueOf10Notes, double valueOfcoins, @NotNull double total, String signatureUrl, CustomerTransactionRequest customerTransactionRequest, List<CashDepositFile> cashDepositFiles) {
        this.accountHolderName = accountHolderName;
        this.accountNumber = accountNumber;
        this.nameOfDepositor = nameOfDepositor;
        this.address = address;
        this.identification = identification;
        this.purposeOfDeposit = purposeOfDeposit;
        this.ammountInWords = ammountInWords;
        this.phoneNumberAndExtn = phoneNumberAndExtn;
        this.date = date;
        this.currency = currency;
        this.valueOf5000Notes = valueOf5000Notes;
        this.valueOf2000Notes = valueOf2000Notes;
        this.valueof1000Notes = valueof1000Notes;
        this.valueOf500Notes = valueOf500Notes;
        this.valueOf100Notes = valueOf100Notes;
        this.valueOf50Notes = valueOf50Notes;
        this.valueOf20Notes = valueOf20Notes;
        this.valueOf10Notes = valueOf10Notes;
        this.valueOfcoins = valueOfcoins;
        this.total = total;
        this.signatureUrl = signatureUrl;
        this.customerTransactionRequest = customerTransactionRequest;
        this.cashDepositFiles = cashDepositFiles;
    }

    public int getCashDepositId() {
        return cashDepositId;
    }

    public void setCashDepositId(int cashDepositId) {
        this.cashDepositId = cashDepositId;
    }

    public String getAccountHolderName() {
        return accountHolderName;
    }

    public void setAccountHolderName(String accountHolderName) {
        this.accountHolderName = accountHolderName;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getNameOfDepositor() {
        return nameOfDepositor;
    }

    public void setNameOfDepositor(String nameOfDepositor) {
        this.nameOfDepositor = nameOfDepositor;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getIdentification() {
        return identification;
    }

    public void setIdentification(String identification) {
        this.identification = identification;
    }

    public String getPurposeOfDeposit() {
        return purposeOfDeposit;
    }

    public void setPurposeOfDeposit(String purposeOfDeposit) {
        this.purposeOfDeposit = purposeOfDeposit;
    }

    public String getAmmountInWords() {
        return ammountInWords;
    }

    public void setAmmountInWords(String ammountInWords) {
        this.ammountInWords = ammountInWords;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
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

    public String getPhoneNumberAndExtn() {
        return phoneNumberAndExtn;
    }

    public void setPhoneNumberAndExtn(String phoneNumberAndExtn) {
        this.phoneNumberAndExtn = phoneNumberAndExtn;
    }

    public List<CashDepositFile> getCashDepositFiles() {
        return cashDepositFiles;
    }

    public void setCashDepositFiles(List<CashDepositFile> cashDepositFiles) {
        this.cashDepositFiles = cashDepositFiles;
    }

    public String getSignatureUrl() {
        return signatureUrl;
    }

    public void setSignatureUrl(String signatureUrl) {
        this.signatureUrl = signatureUrl;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
