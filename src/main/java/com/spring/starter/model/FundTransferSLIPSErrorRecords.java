package com.spring.starter.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "fund_transfer_slips_error_record")
public class FundTransferSLIPSErrorRecords implements Serializable {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private int FundTransferSLIPSErrorRecordsId;

    private String oldValue;

    private String newValue;

    private String comment;

    @ManyToOne
    @JoinColumn(name = "fund_transfer_slips_update_Record_id")
    @JsonIgnore
    private FundTransferSLIPSUpdateRecord fundTransferSLIPSUpdateRecord;

    public FundTransferSLIPSErrorRecords() {
    }

    public FundTransferSLIPSErrorRecords(String oldValue, String newValue, String comment, FundTransferSLIPSUpdateRecord fundTransferSLIPSUpdateRecord) {
        this.oldValue = oldValue;
        this.newValue = newValue;
        this.comment = comment;
        this.fundTransferSLIPSUpdateRecord = fundTransferSLIPSUpdateRecord;
    }

    public int getFundTransferSLIPSErrorRecordsId() {
        return FundTransferSLIPSErrorRecordsId;
    }

    public void setFundTransferSLIPSErrorRecordsId(int fundTransferSLIPSErrorRecordsId) {
        FundTransferSLIPSErrorRecordsId = fundTransferSLIPSErrorRecordsId;
    }

    public String getOldValue() {
        return oldValue;
    }

    public void setOldValue(String oldValue) {
        this.oldValue = oldValue;
    }

    public String getNewValue() {
        return newValue;
    }

    public void setNewValue(String newValue) {
        this.newValue = newValue;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public FundTransferSLIPSUpdateRecord getFundTransferSLIPSUpdateRecord() {
        return fundTransferSLIPSUpdateRecord;
    }

    public void setFundTransferSLIPSUpdateRecord(FundTransferSLIPSUpdateRecord fundTransferSLIPSUpdateRecord) {
        this.fundTransferSLIPSUpdateRecord = fundTransferSLIPSUpdateRecord;
    }
}
