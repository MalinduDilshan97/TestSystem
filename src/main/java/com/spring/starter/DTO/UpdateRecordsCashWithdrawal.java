package com.spring.starter.DTO;

import com.spring.starter.model.CustomerTransactionRequest;

import java.util.List;

public class UpdateRecordsCashWithdrawal {

    private int cashWithdrawalUpdateRecordsId;
    private String signatureUrl;
    private String comment;
    private CustomerTransactionRequest customerTransactionRequest;
    private List<UpdateRecordsDto> cashWithDrawalRecordErrors;

    public UpdateRecordsCashWithdrawal() {
    }

    public UpdateRecordsCashWithdrawal(int cashWithdrawalUpdateRecordsId, String signatureUrl, String comment,
                                       CustomerTransactionRequest customerTransactionRequest, List<UpdateRecordsDto> cashWithDrawalRecordErrors) {
        this.cashWithdrawalUpdateRecordsId = cashWithdrawalUpdateRecordsId;
        this.signatureUrl = signatureUrl;
        this.comment = comment;
        this.customerTransactionRequest = customerTransactionRequest;
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

    public CustomerTransactionRequest getCustomerTransactionRequest() {
        return customerTransactionRequest;
    }

    public void setCustomerTransactionRequest(CustomerTransactionRequest customerTransactionRequest) {
        this.customerTransactionRequest = customerTransactionRequest;
    }

    public List<UpdateRecordsDto> getCashWithDrawalRecordErrors() {
        return cashWithDrawalRecordErrors;
    }

    public void setCashWithDrawalRecordErrors(List<UpdateRecordsDto> cashWithDrawalRecordErrors) {
        this.cashWithDrawalRecordErrors = cashWithDrawalRecordErrors;
    }
}
