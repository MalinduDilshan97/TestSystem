package com.spring.starter.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name = "cash_withdrawal_record_erros")
public class CashWithDrawalRecordErrors {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private int cashWithDrawalRecordErrorsId;

    private String oldValue;

    private String newValue;

    private String comment;

    @ManyToOne
    @JoinColumn(name = "cash_withdrawal_updates_Record_id")
    @JsonIgnore
    private CashWithdrawalUpdateRecords cashWithdrawalUpdateRecords;

    public CashWithDrawalRecordErrors() {
    }

    public CashWithDrawalRecordErrors(String oldValue, String newValue, String comment) {
        this.oldValue = oldValue;
        this.newValue = newValue;
        this.comment = comment;
    }

    public int getCashWithDrawalRecordErrorsId() {
        return cashWithDrawalRecordErrorsId;
    }

    public void setCashWithDrawalRecordErrorsId(int cashWithDrawalRecordErrorsId) {
        this.cashWithDrawalRecordErrorsId = cashWithDrawalRecordErrorsId;
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

    public CashWithdrawalUpdateRecords getCashWithdrawalUpdateRecords() {
        return cashWithdrawalUpdateRecords;
    }

    public void setCashWithdrawalUpdateRecords(CashWithdrawalUpdateRecords cashWithdrawalUpdateRecords) {
        this.cashWithdrawalUpdateRecords = cashWithdrawalUpdateRecords;
    }
}
