package com.spring.starter.model;


import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Table(name = "effect_or_revoke_payment")
public class EffectOrRevokePayment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int effectOrRevokePaymentId;
    private String status;
    @NotNull
    private String customerAccountNo;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_service_request_id")
    private CustomerServiceRequest customerServiceRequest;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "effectOrRevokePaymentId")
    private List<EffectOrRevokePaymentDetails> effectOrRevokePaymentDetails;


    private boolean softReject = false;


    public EffectOrRevokePayment() {
    }

    public boolean isSoftReject() {
        return softReject;
    }

    public void setSoftReject(boolean softReject) {
        this.softReject = softReject;
    }

    public int getEffectOrRevokePaymentId() {
        return effectOrRevokePaymentId;
    }

    public void setEffectOrRevokePaymentId(int effectOrRevokePaymentId) {
        this.effectOrRevokePaymentId = effectOrRevokePaymentId;
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
