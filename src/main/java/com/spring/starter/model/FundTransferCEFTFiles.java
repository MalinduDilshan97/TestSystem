package com.spring.starter.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
public class FundTransferCEFTFiles {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;
    private String fileType;
    private String fileUrl;
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fundTransferCEFTFiles")
    private FundTransferCEFT fundTransferCEFTs;

    public FundTransferCEFTFiles() {
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
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

    public FundTransferCEFT getFundTransferCEFTS() {
        return fundTransferCEFTs;
    }

    public void setFundTransferCEFTS(FundTransferCEFT fundTransferCEFTS) {
        this.fundTransferCEFTs = fundTransferCEFTS;
    }
}
