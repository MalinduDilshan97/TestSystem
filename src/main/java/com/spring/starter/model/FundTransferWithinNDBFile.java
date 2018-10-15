package com.spring.starter.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "fund_transfer_within_ndb_File")
public class FundTransferWithinNDBFile implements Serializable {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private int fundTransferWithinNDBFileId;

    private String fileType;

    private String fileUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fundTransferWithinNdbId")
    @JsonIgnore
    private FundTransferWithinNDB fundTransferWithinNDB;

    public FundTransferWithinNDBFile() {
    }

    public FundTransferWithinNDBFile(String fileType, String fileUrl, FundTransferWithinNDB fundTransferWithinNDB) {
        this.fileType = fileType;
        this.fileUrl = fileUrl;
        this.fundTransferWithinNDB = fundTransferWithinNDB;
    }

    public int getFundTransferWithinNDBFileId() {
        return fundTransferWithinNDBFileId;
    }

    public void setFundTransferWithinNDBFileId(int fundTransferWithinNDBFileId) {
        this.fundTransferWithinNDBFileId = fundTransferWithinNDBFileId;
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

    public FundTransferWithinNDB getFundTransferWithinNDB() {
        return fundTransferWithinNDB;
    }

    public void setFundTransferWithinNDB(FundTransferWithinNDB fundTransferWithinNDB) {
        this.fundTransferWithinNDB = fundTransferWithinNDB;
    }
}
