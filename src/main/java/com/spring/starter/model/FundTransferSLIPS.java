package com.spring.starter.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "fund_transfer_SLIPS")
public class FundTransferSLIPS {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int fundTransferSLIPSId;

    @NotNull
    private String creditAccountNo;

    @NotNull
    @Size(min = 2)
    @Pattern(regexp = "^([A-Za-z0-9_\\s])*$")
    private String accountName;

    @NotNull
    private double ammount;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bank_Id")
    @NotNull
    private Bank bank;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "branch_Id")
    @NotNull
    private Branch branch;

    @NotNull
    @Size(min = 2)
    @Pattern(regexp = "^([A-Za-z0-9_\\s])*$")
    private String reason;

    private String url;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customerTransactionRequestId")
    private CustomerTransactionRequest customerTransactionRequest;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "fundTransferSLIPSFiles")
    private List<FundTransferSLIPSFiles> fundTransferSLIPSIds;

    public FundTransferSLIPS() {
    }

    public FundTransferSLIPS(@NotNull String creditAccountNo, @NotNull @Size(min = 2) @Pattern(regexp = "^([A-Za-z0-9_\\s])*$") String accountName,
                             @NotNull double ammount, @NotNull Bank bank, @NotNull Branch branch,
                             @NotNull @Size(min = 2) @Pattern(regexp = "^([A-Za-z0-9_\\s])*$") String reason,
                             CustomerTransactionRequest customerTransactionRequest) {
        this.creditAccountNo = creditAccountNo;
        this.accountName = accountName;
        this.ammount = ammount;
        this.bank = bank;
        this.branch = branch;
        this.reason = reason;
        this.customerTransactionRequest = customerTransactionRequest;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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

    public Branch getBranch() {
        return branch;
    }

    public void setBranch(Branch branch) {
        this.branch = branch;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public int getFundTransferSLIPSId() {
        return fundTransferSLIPSId;
    }

    public void setFundTransferSLIPSId(int fundTransferSLIPSId) {
        this.fundTransferSLIPSId = fundTransferSLIPSId;
    }

    public CustomerTransactionRequest getCustomerTransactionRequest() {
        return customerTransactionRequest;
    }

    public void setCustomerTransactionRequest(CustomerTransactionRequest customerTransactionRequest) {
        this.customerTransactionRequest = customerTransactionRequest;
    }

    public List<FundTransferSLIPSFiles> getFundTransferSLIPSIds() {
        return fundTransferSLIPSIds;
    }

    public void setFundTransferSLIPSIds(List<FundTransferSLIPSFiles> fundTransferSLIPSIds) {
        this.fundTransferSLIPSIds = fundTransferSLIPSIds;
    }
}
