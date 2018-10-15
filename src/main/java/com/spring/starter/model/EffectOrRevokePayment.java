package com.spring.starter.model;


import javax.persistence.*;
import javax.validation.constraints.NotNull;

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

    public EffectOrRevokePayment() {
    }

    public EffectOrRevokePayment(String status, @NotNull String customerAccountNo, CustomerServiceRequest customerServiceRequest) {
        this.status = status;
        this.customerAccountNo = customerAccountNo;
        this.customerServiceRequest = customerServiceRequest;
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
}
