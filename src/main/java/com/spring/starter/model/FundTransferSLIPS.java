package com.spring.starter.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

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

    @NotNull
    @Size(min = 2)
    @Pattern(regexp = "^([A-Za-z0-9_\\s])*$")
    private String creditingAccountBank;

    @NotNull
    @Size(min = 2)
    @Pattern(regexp = "^([A-Za-z0-9_\\s])*$")
    private String branch;

    @NotNull
    @Size(min = 2)
    @Pattern(regexp = "^([A-Za-z0-9_\\s])*$")
    private String reason;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customerTransactionRequestId")
    private CustomerTransactionRequest customerTransactionRequest;

    public FundTransferSLIPS() {
    }

    public FundTransferSLIPS(@NotNull String creditAccountNo, @NotNull @Size(min = 2) @Pattern(regexp = "^([A-Za-z0-9_\\s])*$") String accountName, @NotNull double ammount, @NotNull @Size(min = 2) @Pattern(regexp = "^([A-Za-z0-9_\\s])*$") String creditingAccountBank, @NotNull @Size(min = 2) @Pattern(regexp = "^([A-Za-z0-9_\\s])*$") String branch, @NotNull @Size(min = 2) @Pattern(regexp = "^([A-Za-z0-9_\\s])*$") String reason) {
        this.creditAccountNo = creditAccountNo;
        this.accountName = accountName;
        this.ammount = ammount;
        this.creditingAccountBank = creditingAccountBank;
        this.branch = branch;
        this.reason = reason;
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

    public String getCreditingAccountBank() {
        return creditingAccountBank;
    }

    public void setCreditingAccountBank(String creditingAccountBank) {
        this.creditingAccountBank = creditingAccountBank;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
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
}
