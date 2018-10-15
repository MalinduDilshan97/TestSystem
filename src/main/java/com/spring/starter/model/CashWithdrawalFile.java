package com.spring.starter.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name = "cash_withdrawal_file")
public class CashWithdrawalFile {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private int cashWithdrawalFileId;

    private String fileType;

    private String fileUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cashWithdrawalId")
    @JsonIgnore
    private CashWithdrawal cashWithdrawal;

    public CashWithdrawalFile() {
    }

    public CashWithdrawalFile(String fileType, String fileUrl) {
        this.fileType = fileType;
        this.fileUrl = fileUrl;
    }

    public int getCashWithdrawalFileId() {
        return cashWithdrawalFileId;
    }

    public void setCashWithdrawalFileId(int cashWithdrawalFileId) {
        this.cashWithdrawalFileId = cashWithdrawalFileId;
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

    public CashWithdrawal getCashWithdrawal() {
        return cashWithdrawal;
    }

    public void setCashWithdrawal(CashWithdrawal cashWithdrawal) {
        this.cashWithdrawal = cashWithdrawal;
    }
}
