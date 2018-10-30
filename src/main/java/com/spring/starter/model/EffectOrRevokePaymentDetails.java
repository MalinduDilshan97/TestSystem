package com.spring.starter.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Date;
import javax.persistence.*;

@Entity
@Table(name = "effect_or_revoke_payment_details")
public class EffectOrRevokePaymentDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int effectOrRevokePaymentDetailsId;
    private String chequeNumber;
    private double value;
    private String payeeName;
    private Date dateOfCheque;
    private String reason;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "effectOrRevokePaymentId")
    @JsonIgnore
    private EffectOrRevokePayment effectOrRevokePayment;

    public EffectOrRevokePaymentDetails() {
    }

    public EffectOrRevokePaymentDetails(String chequeNumber, double value, String payeeName, Date dateOfCheque, String reason, EffectOrRevokePayment effectOrRevokePayment) {
        this.chequeNumber = chequeNumber;
        this.value = value;
        this.payeeName = payeeName;
        this.dateOfCheque = dateOfCheque;
        this.reason = reason;
        this.effectOrRevokePayment = effectOrRevokePayment;
    }

    public int getEffectOrRevokePaymentDetailsId() {
        return effectOrRevokePaymentDetailsId;
    }

    public void setEffectOrRevokePaymentDetailsId(int effectOrRevokePaymentDetailsId) {
        this.effectOrRevokePaymentDetailsId = effectOrRevokePaymentDetailsId;
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

    public Date getDateOfCheque() {
        return dateOfCheque;
    }

    public void setDateOfCheque(Date dateOfCheque) {
        this.dateOfCheque = dateOfCheque;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public EffectOrRevokePayment getEffectOrRevokePayment() {
        return effectOrRevokePayment;
    }

    public void setEffectOrRevokePayment(EffectOrRevokePayment effectOrRevokePayment) {
        this.effectOrRevokePayment = effectOrRevokePayment;
    }
}
