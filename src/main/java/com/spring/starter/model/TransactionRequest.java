package com.spring.starter.model;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name = "transaction_request")
public class TransactionRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int transactionRequestId;

    @Column
    @NotNull
    private String transactionRequestName;

    @Column
    @NotNull
    private String digiFormType;

    @Column(unique=true , nullable=false)
    @NotNull
    private int digiFormId;

    @Column
    private Date date;

    public TransactionRequest() {
    }

    public TransactionRequest(String transactionRequestName, String digiFormType, int digiFormId, Date date) {
        this.transactionRequestName = transactionRequestName;
        this.digiFormType = digiFormType;
        this.digiFormId = digiFormId;
        this.date = date;
    }

    public TransactionRequest(String transactionRequestName, String digiFormType, int digiFormId) {
        this.transactionRequestName = transactionRequestName;
        this.digiFormType = digiFormType;
        this.digiFormId = digiFormId;
    }

    public int getTransactionRequestId() {
        return transactionRequestId;
    }

    public void setTransactionRequestId(int transactionRequestId) {
        this.transactionRequestId = transactionRequestId;
    }

    public String getTransactionRequestName() {
        return transactionRequestName;
    }

    public void setTransactionRequestName(String transactionRequestName) {
        this.transactionRequestName = transactionRequestName;
    }

    public String getDigiFormType() {
        return digiFormType;
    }

    public void setDigiFormType(String digiFormType) {
        this.digiFormType = digiFormType;
    }

    public int getDigiFormId() {
        return digiFormId;
    }

    public void setDigiFormId(int digiFormId) {
        this.digiFormId = digiFormId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
