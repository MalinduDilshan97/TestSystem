package com.spring.starter.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "fund_transfer_slip_update_record")
public class FundTransferSLIPSUpdateRecord implements Serializable {


    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private int FundTransferSLIPSUpdateRecordId;

    private String signatureUrl;

    private String comment;

    @ManyToOne
    @JoinColumn(name = "customer_transactions_request_id")
    private CustomerTransactionRequest customerTransactionRequest;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "fund_transfer_slips_error_records")
    private List<FundTransferSLIPSErrorRecords> fundTransferSLIPSErrorRecords;

    public FundTransferSLIPSUpdateRecord() {
    }

    public FundTransferSLIPSUpdateRecord(String signatureUrl, String comment, CustomerTransactionRequest customerTransactionRequest, List<FundTransferSLIPSErrorRecords> fundTransferSLIPSErrorRecords) {
        this.signatureUrl = signatureUrl;
        this.comment = comment;
        this.customerTransactionRequest = customerTransactionRequest;
        this.fundTransferSLIPSErrorRecords = fundTransferSLIPSErrorRecords;
    }

    public int getFundTransferSLIPSUpdateRecordId() {
        return FundTransferSLIPSUpdateRecordId;
    }

    public void setFundTransferSLIPSUpdateRecordId(int fundTransferSLIPSUpdateRecordId) {
        FundTransferSLIPSUpdateRecordId = fundTransferSLIPSUpdateRecordId;
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

    public List<FundTransferSLIPSErrorRecords> getFundTransferSLIPSErrorRecords() {
        return fundTransferSLIPSErrorRecords;
    }

    public void setFundTransferSLIPSErrorRecords(List<FundTransferSLIPSErrorRecords> fundTransferSLIPSErrorRecords) {
        this.fundTransferSLIPSErrorRecords = fundTransferSLIPSErrorRecords;
    }
}
