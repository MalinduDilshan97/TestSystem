package com.spring.starter.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "fund_transfer_within_ndb_update_record")
public class FundTransferWithinNDBUpdateRecord implements Serializable {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private int FundTransferWithinNDBUpdateRecordId;

    private String signatureUrl;

    private String comment;

    @ManyToOne
    @JoinColumn(name = "customer_transactions_request_id")
    private CustomerTransactionRequest customerTransactionRequest;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "fund_transaction_within_ndb_record_error_id")
    private List<FundTransferWithinNDBErrorRecords> fundTransferWithinNDBErrorRecords;

    public FundTransferWithinNDBUpdateRecord() {
    }

    public FundTransferWithinNDBUpdateRecord(String signatureUrl, String comment, CustomerTransactionRequest customerTransactionRequest, List<FundTransferWithinNDBErrorRecords> fundTransferWithinNDBErrorRecords) {
        this.signatureUrl = signatureUrl;
        this.comment = comment;
        this.customerTransactionRequest = customerTransactionRequest;
        this.fundTransferWithinNDBErrorRecords = fundTransferWithinNDBErrorRecords;
    }

    public int getFundTransferWithinNDBUpdateRecordId() {
        return FundTransferWithinNDBUpdateRecordId;
    }

    public void setFundTransferWithinNDBUpdateRecordId(int fundTransferWithinNDBUpdateRecordId) {
        FundTransferWithinNDBUpdateRecordId = fundTransferWithinNDBUpdateRecordId;
    }

    public String getSignatureUrl() {
        return signatureUrl;
    }

    public void setSignatureUrl(String signatureUrl) {
        this.signatureUrl = signatureUrl;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public CustomerTransactionRequest getCustomerTransactionRequest() {
        return customerTransactionRequest;
    }

    public void setCustomerTransactionRequest(CustomerTransactionRequest customerTransactionRequest) {
        this.customerTransactionRequest = customerTransactionRequest;
    }

    public List<FundTransferWithinNDBErrorRecords> getFundTransferWithinNDBErrorRecords() {
        return fundTransferWithinNDBErrorRecords;
    }

    public void setFundTransferWithinNDBErrorRecords(List<FundTransferWithinNDBErrorRecords> fundTransferWithinNDBErrorRecords) {
        this.fundTransferWithinNDBErrorRecords = fundTransferWithinNDBErrorRecords;
    }
}
