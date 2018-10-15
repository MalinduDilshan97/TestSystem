package com.spring.starter.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "cash_deposit_error_records")
public class CashDepositErrorRecords implements Serializable {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private int cashWithDrawalRecordErrorsId;

    private String oldValue;

    private String newValue;

    private String comment;

    @ManyToOne
    @JoinColumn(name = "cash_deposit_update_Record_id")
    @JsonIgnore
    private CashDepositUpdateRecords cashDepositUpdateRecords;

    public CashDepositErrorRecords() {
    }

    public CashDepositErrorRecords(String oldValue, String newValue, String comment, CashDepositUpdateRecords cashDepositUpdateRecords) {
        this.oldValue = oldValue;
        this.newValue = newValue;
        this.comment = comment;
        this.cashDepositUpdateRecords = cashDepositUpdateRecords;
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

    public CashDepositUpdateRecords getCashDepositUpdateRecords() {
        return cashDepositUpdateRecords;
    }

    public void setCashDepositUpdateRecords(CashDepositUpdateRecords cashDepositUpdateRecords) {
        this.cashDepositUpdateRecords = cashDepositUpdateRecords;
    }
}
