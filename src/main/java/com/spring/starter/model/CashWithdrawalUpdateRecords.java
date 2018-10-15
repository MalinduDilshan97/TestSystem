package com.spring.starter.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "cash_withdrawal_update_records")
public class CashWithdrawalUpdateRecords {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private int cashWithdrawalUpdateRecordsId;

    private String signatureUrl;

    private String comment;

    @ManyToOne
    @JoinColumn(name = "customer_transactions_request_id")
    private CustomerTransactionRequest customerTransactionRequest;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "cash_withdrawal_record_error_id")
    private List<CashWithDrawalRecordErrors> cashWithDrawalRecordErrors;


    public CashWithdrawalUpdateRecords() {
    }

    public CashWithdrawalUpdateRecords(int cashWithdrawalUpdateRecordsId, String signatureUrl, String comment,
                                       List<CashWithDrawalRecordErrors> cashWithDrawalRecordErrors) {
        this.cashWithdrawalUpdateRecordsId = cashWithdrawalUpdateRecordsId;
        this.signatureUrl = signatureUrl;
        this.comment = comment;
        this.cashWithDrawalRecordErrors = cashWithDrawalRecordErrors;
    }

    public int getCashWithdrawalUpdateRecordsId() {
        return cashWithdrawalUpdateRecordsId;
    }

    public void setCashWithdrawalUpdateRecordsId(int cashWithdrawalUpdateRecordsId) {
        this.cashWithdrawalUpdateRecordsId = cashWithdrawalUpdateRecordsId;
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

    public List<CashWithDrawalRecordErrors> getCashWithDrawalRecordErrors() {
        return cashWithDrawalRecordErrors;
    }

    public void setCashWithDrawalRecordErrors(List<CashWithDrawalRecordErrors> cashWithDrawalRecordErrors) {
        this.cashWithDrawalRecordErrors = cashWithDrawalRecordErrors;
    }

    public CustomerTransactionRequest getCustomerTransactionRequest() {
        return customerTransactionRequest;
    }

    public void setCustomerTransactionRequest(CustomerTransactionRequest customerTransactionRequest) {
        this.customerTransactionRequest = customerTransactionRequest;
    }
}
