package com.spring.starter.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "cash_deposit_update_records")
public class CashDepositUpdateRecords implements Serializable {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private int cashDepositUpdateRecordsId;

    private String signatureUrl;

    private String comment;

    @ManyToOne
    @JoinColumn(name = "customer_transactions_request_id")
    private CustomerTransactionRequest customerTransactionRequest;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "cash_deposit_record_error_id")
    private List<CashDepositErrorRecords> cashDepositErrorRecords;

    public CashDepositUpdateRecords() {
    }

    public CashDepositUpdateRecords(String signatureUrl, String comment, CustomerTransactionRequest customerTransactionRequest, List<CashDepositErrorRecords> cashDepositErrorRecords) {
        this.signatureUrl = signatureUrl;
        this.comment = comment;
        this.customerTransactionRequest = customerTransactionRequest;
        this.cashDepositErrorRecords = cashDepositErrorRecords;
    }

    public int getCashDepositUpdateRecordsId() {
        return cashDepositUpdateRecordsId;
    }

    public void setCashDepositUpdateRecordsId(int cashDepositUpdateRecordsId) {
        this.cashDepositUpdateRecordsId = cashDepositUpdateRecordsId;
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

    public List<CashDepositErrorRecords> getCashDepositErrorRecords() {
        return cashDepositErrorRecords;
    }

    public void setCashDepositErrorRecords(List<CashDepositErrorRecords> cashDepositErrorRecords) {
        this.cashDepositErrorRecords = cashDepositErrorRecords;
    }
}
