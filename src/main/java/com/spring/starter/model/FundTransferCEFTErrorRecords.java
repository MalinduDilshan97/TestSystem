package com.spring.starter.model;

import javax.persistence.*;

@Entity
@Table(name = "fund_transfer_CEFTE_error_records")
public class FundTransferCEFTErrorRecords {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int fundTransferCEFTErrorRecordsId;

    private String oldValue;

    private String newValue;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bill_payment_update_records")
    private FundTransferCEFTUpdateRecords fundTransferCEFTUpdateRecords;

    public FundTransferCEFTErrorRecords() {
    }

    public FundTransferCEFTErrorRecords(String oldValue, String newValue, FundTransferCEFTUpdateRecords fundTransferCEFTUpdateRecords) {
        this.oldValue = oldValue;
        this.newValue = newValue;
        this.fundTransferCEFTUpdateRecords = fundTransferCEFTUpdateRecords;
    }

    public int getFundTransferCEFTErrorRecordsId() {
        return fundTransferCEFTErrorRecordsId;
    }

    public void setFundTransferCEFTErrorRecordsId(int fundTransferCEFTErrorRecordsId) {
        this.fundTransferCEFTErrorRecordsId = fundTransferCEFTErrorRecordsId;
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

    public FundTransferCEFTUpdateRecords getFundTransferCEFTUpdateRecords() {
        return fundTransferCEFTUpdateRecords;
    }

    public void setFundTransferCEFTUpdateRecords(FundTransferCEFTUpdateRecords fundTransferCEFTUpdateRecords) {
        this.fundTransferCEFTUpdateRecords = fundTransferCEFTUpdateRecords;
    }
}
