package com.spring.starter.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name = "fund_transfer_SLIPS_files")
public class FundTransferSLIPSFiles {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private int fundTransferSLIPSFilesId;

    private String fileType;

    private String fileUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cashWithdrawalId")
    @JsonIgnore
    private FundTransferSLIPS fundTransferSLIPS;

    public FundTransferSLIPSFiles() {
    }

    public FundTransferSLIPSFiles(String fileType, String fileUrl, FundTransferSLIPS fundTransferSLIPS) {
        this.fileType = fileType;
        this.fileUrl = fileUrl;
        this.fundTransferSLIPS = fundTransferSLIPS;
    }

    public int getFundTransferSLIPSFilesId() {
        return fundTransferSLIPSFilesId;
    }

    public void setFundTransferSLIPSFilesId(int fundTransferSLIPSFilesId) {
        this.fundTransferSLIPSFilesId = fundTransferSLIPSFilesId;
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

    public FundTransferSLIPS getFundTransferSLIPS() {
        return fundTransferSLIPS;
    }

    public void setFundTransferSLIPS(FundTransferSLIPS fundTransferSLIPS) {
        this.fundTransferSLIPS = fundTransferSLIPS;
    }
}
