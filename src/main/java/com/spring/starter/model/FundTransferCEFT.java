package com.spring.starter.model;

import org.springframework.web.bind.annotation.RequestMapping;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "fund_transfer_CEFT")
public class FundTransferCEFT {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int fundTransferCEFTId;

    @NotNull
    private String creditAccountNo;

    @NotNull
    @Size(min = 3)
    @Pattern(regexp = "^([A-Za-z0-9_\\s])*$")
    private String accountName;

    @NotNull
    private double ammount;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bankId")
    private Bank bank;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brancbId")
    private Branch branch;

    @NotNull
    @Size(min = 2)
    @Pattern(regexp = "^([A-Za-z0-9_\\s])*$")
    private String reason;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customerTransactionRequestId")
    private CustomerTransactionRequest customerTransactionRequest;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "fundTransferCEFTFiles")
    private List<FundTransferCEFTFiles> fundTransferCEFTFiles;

    private String url;

    public FundTransferCEFT(@NotNull String creditAccountNo, @NotNull @Size(min = 3) @Pattern(regexp = "^([A-Za-z0-9_\\s])*$") String accountName, @NotNull double ammount, Bank bank, Branch branch, @NotNull @Size(min = 2) @Pattern(regexp = "^([A-Za-z0-9_\\s])*$") String reason, CustomerTransactionRequest customerTransactionRequest, String url) {
        this.creditAccountNo = creditAccountNo;
        this.accountName = accountName;
        this.ammount = ammount;
        this.bank = bank;
        this.branch = branch;
        this.reason = reason;
        this.customerTransactionRequest = customerTransactionRequest;
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Branch getBranch() {
        return branch;
    }

    public void setBranch(Branch branch) {
        this.branch = branch;
    }

    public FundTransferCEFT() {
    }

    public int getFundTransferCEFTId() {
        return fundTransferCEFTId;
    }

    public void setFundTransferCEFTId(int fundTransferCEFTId) {
        this.fundTransferCEFTId = fundTransferCEFTId;
    }

    public String getCreditAccountNo() {
        return creditAccountNo;
    }

    public void setCreditAccountNo(String creditAccountNo) {
        this.creditAccountNo = creditAccountNo;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public double getAmmount() {
        return ammount;
    }

    public void setAmmount(double ammount) {
        this.ammount = ammount;
    }

    public Bank getBank() {
        return bank;
    }

    public void setBank(Bank bank) {
        this.bank = bank;
    }



    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public CustomerTransactionRequest getCustomerTransactionRequest() {
        return customerTransactionRequest;
    }

    public void setCustomerTransactionRequest(CustomerTransactionRequest customerTransactionRequest) {
        this.customerTransactionRequest = customerTransactionRequest;
    }

    public List<FundTransferCEFTFiles> getFundTransferCEFTFiles() {
        return fundTransferCEFTFiles;
    }

    public void setFundTransferCEFTFiles(List<FundTransferCEFTFiles> fundTransferCEFTFiles) {
        this.fundTransferCEFTFiles = fundTransferCEFTFiles;
    }
}
