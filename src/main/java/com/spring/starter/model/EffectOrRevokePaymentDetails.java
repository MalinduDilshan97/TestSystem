package com.spring.starter.model;

import java.util.Date;
import javax.persistence.*;

@Entity
@Table(name = "effect_or_revoke_payment_details")
public class EffectOrRevokePaymentDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int EffectOrRevokePaymentDetailsId;
    private String chequeNumber;
    private double value;
    private String payeeName;
    private Date dateOfCheque;
    private String reason;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "EffectOrRevokePaymentId")
    private EffectOrRevokePayment effectOrRevokePayment;

    public EffectOrRevokePaymentDetails() {
    }

    public EffectOrRevokePaymentDetails(int effectOrRevokePaymentDetailsId, String chequeNumber, double value, String payeeName, Date dateOfCheque, String reason, EffectOrRevokePayment effectOrRevokePayment) {
        EffectOrRevokePaymentDetailsId = effectOrRevokePaymentDetailsId;
        this.chequeNumber = chequeNumber;
        this.value = value;
        this.payeeName = payeeName;
        this.dateOfCheque = dateOfCheque;
        this.reason = reason;
        this.effectOrRevokePayment = effectOrRevokePayment;
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
