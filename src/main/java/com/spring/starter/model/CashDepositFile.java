package com.spring.starter.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "cash_deposit_file")
public class CashDepositFile implements Serializable {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private int cashDepositFileId;

    private String fileType;

    private String fileUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cashWithdrawalId")
    @JsonIgnore
    private CashDeposit cashDeposit;

    public CashDepositFile() {
    }

    public CashDepositFile(String fileType, String fileUrl) {
        this.fileType = fileType;
        this.fileUrl = fileUrl;
    }

    public int getCashDepositFileId() {
        return cashDepositFileId;
    }

    public void setCashDepositFileId(int cashDepositFileId) {
        this.cashDepositFileId = cashDepositFileId;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public CashDeposit getCashDeposit() {
        return cashDeposit;
    }

    public void setCashDeposit(CashDeposit cashDeposit) {
        this.cashDeposit = cashDeposit;
    }
}
