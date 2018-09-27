package com.spring.starter.DTO;

import java.io.Serializable;

public class EffectOrRevokePaymentDetailsDTO implements Serializable {

    private int EffectOrRevokePaymentDetailsId;
    private String chequeNumber;
    private double value;
    private String payeeName;
    private String dateOfCheque;
    private String reason;

    public EffectOrRevokePaymentDetailsDTO() {
    }

    public EffectOrRevokePaymentDetailsDTO(String chequeNumber, double value, String payeeName, String dateOfCheque, String reason) {
        this.chequeNumber = chequeNumber;
        this.value = value;
        this.payeeName = payeeName;
        this.dateOfCheque = dateOfCheque;
        this.reason = reason;
    }

    public EffectOrRevokePaymentDetailsDTO(int effectOrRevokePaymentDetailsId, String chequeNumber, double value, String payeeName, String dateOfCheque, String reason) {
        EffectOrRevokePaymentDetailsId = effectOrRevokePaymentDetailsId;
        this.chequeNumber = chequeNumber;
        this.value = value;
        this.payeeName = payeeName;
        this.dateOfCheque = dateOfCheque;
        this.reason = reason;
    }

    public int getEffectOrRevokePaymentDetailsId() {
        return EffectOrRevokePaymentDetailsId;
    }

    public void setEffectOrRevokePaymentDetailsId(int effectOrRevokePaymentDetailsId) {
        EffectOrRevokePaymentDetailsId = effectOrRevokePaymentDetailsId;
    }

    public String getChequeNumber() {
        return chequeNumber;
    }

    public void setChequeNumber(String chequeNumber) {
        this.chequeNumber = chequeNumber;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public String getPayeeName() {
        return payeeName;
    }

    public void setPayeeName(String payeeName) {
        this.payeeName = payeeName;
    }

    public String getDateOfCheque() {
        return dateOfCheque;
    }

    public void setDateOfCheque(String dateOfCheque) {
        this.dateOfCheque = dateOfCheque;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
