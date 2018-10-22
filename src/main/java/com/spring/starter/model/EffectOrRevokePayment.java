package com.spring.starter.model;


import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Table(name = "effect_or_revoke_payment")
public class EffectOrRevokePayment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int EffectOrRevokePaymentId;
    private String status;
    @NotNull
    private String customerAccountNo;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_service_request_id")
    private CustomerServiceRequest customerServiceRequest;

    @OneToMany
    @JoinColumn(name = "effect_revoke_details_id")
    private List<EffectOrRevokePaymentDetails> effectOrRevokePaymentDetails;

    private boolean softReject = false;


    public EffectOrRevokePayment() {
    }

    public EffectOrRevokePayment(String status, @NotNull String customerAccountNo, CustomerServiceRequest customerServiceRequest) {
        this.status = status;
        this.customerAccountNo = customerAccountNo;
        this.customerServiceRequest = customerServiceRequest;
    }

    public boolean isSoftReject() {
        return softReject;
    }

    public void setSoftReject(boolean softReject) {
        this.softReject = softReject;
    }

    public int getEffectOrRevokePaymentId() {
        return EffectOrRevokePaymentId;
    }

    public void setEffectOrRevokePaymentId(int effectOrRevokePaymentId) {
        EffectOrRevokePaymentId = effectOrRevokePaymentId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCustomerAccountNo() {
        return customerAccountNo;
    }

    public void setCustomerAccountNo(String customerAccountNo) {
        this.customerAccountNo = customerAccountNo;
    }

    public CustomerServiceRequest getCustomerServiceRequest() {
        return customerServiceRequest;
    }

    public void setCustomerServiceRequest(CustomerServiceRequest customerServiceRequest) {
        this.customerServiceRequest = customerServiceRequest;
    }

    public List<EffectOrRevokePaymentDetails> getEffectOrRevokePaymentDetails() {
        return effectOrRevokePaymentDetails;
    }

    public void setEffectOrRevokePaymentDetails(List<EffectOrRevokePaymentDetails> effectOrRevokePaymentDetails) {
        this.effectOrRevokePaymentDetails = effectOrRevokePaymentDetails;
    }
}
