package com.spring.starter.DTO;


public class FundTransferCEFTDTO {


    private String creditAccountNo;

    private String accountName;

    private String accountNumber;

    private double ammount;

    private int bankId;

    private int branchId;

    private String reason;

    public FundTransferCEFTDTO() {
    }

    public FundTransferCEFTDTO(String creditAccountNo, String accountName, double ammount, int bankId, int branchId, String reason) {
        this.creditAccountNo = creditAccountNo;
        this.accountName = accountName;
        this.ammount = ammount;
        this.bankId = bankId;
        this.branchId = branchId;
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

    public int getBankId() {
        return bankId;
    }

    public void setBankId(int bankId) {
        this.bankId = bankId;
    }

    public int getBranchId() {
        return branchId;
    }

    public void setBranchId(int branchId) {
        this.branchId = branchId;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }
}
