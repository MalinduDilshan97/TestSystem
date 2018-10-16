package com.spring.starter.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name = "fund_transfer_within_ndb_error_record")
public class FundTransferWithinNDBErrorRecords {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private int FundTransferWithinNDBErrorRecordsId;

    private String oldValue;

    private String newValue;

    private String comment;

    @ManyToOne
    @JoinColumn(name = "cash_deposit_update_Record_id")
    @JsonIgnore
    private FundTransferWithinNDBUpdateRecord FundTransferWithinNDBUpdateRecord;

    public FundTransferWithinNDBErrorRecords() {
    }

    public FundTransferWithinNDBErrorRecords(String oldValue, String newValue, String comment, com.spring.starter.model.FundTransferWithinNDBUpdateRecord fundTransferWithinNDBUpdateRecord) {
        this.oldValue = oldValue;
        this.newValue = newValue;
        this.comment = comment;
        FundTransferWithinNDBUpdateRecord = fundTransferWithinNDBUpdateRecord;
    }

    public int getFundTransferWithinNDBErrorRecordsId() {
        return FundTransferWithinNDBErrorRecordsId;
    }

    public void setFundTransferWithinNDBErrorRecordsId(int fundTransferWithinNDBErrorRecordsId) {
        FundTransferWithinNDBErrorRecordsId = fundTransferWithinNDBErrorRecordsId;
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

    public com.spring.starter.model.FundTransferWithinNDBUpdateRecord getFundTransferWithinNDBUpdateRecord() {
        return FundTransferWithinNDBUpdateRecord;
    }

    public void setFundTransferWithinNDBUpdateRecord(com.spring.starter.model.FundTransferWithinNDBUpdateRecord fundTransferWithinNDBUpdateRecord) {
        FundTransferWithinNDBUpdateRecord = fundTransferWithinNDBUpdateRecord;
    }
}
