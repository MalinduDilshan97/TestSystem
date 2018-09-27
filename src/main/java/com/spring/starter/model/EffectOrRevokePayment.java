package com.spring.starter.model;


import javax.persistence.*;

@Entity
@Table(name = "effect_or_revoke_payment")
public class EffectOrRevokePayment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int EffectOrRevokePaymentId;
    private String status;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CustomerAccountNoId")
    private CustomerAccountNo customerAccountNo;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_service_request_id")
    private CustomerServiceRequest customerServiceRequest;

    public EffectOrRevokePayment() {
    }

    public EffectOrRevokePayment(int effectOrRevokePaymentId, String status, CustomerAccountNo customerAccountNo, CustomerServiceRequest customerServiceRequest) {
        EffectOrRevokePaymentId = effectOrRevokePaymentId;
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

    public CustomerAccountNo getCustomerAccountNo() {
        return customerAccountNo;
    }

    public void setCustomerAccountNo(CustomerAccountNo customerAccountNo) {
        this.customerAccountNo = customerAccountNo;
    }

    public CustomerServiceRequest getCustomerServiceRequest() {
        return customerServiceRequest;
    }

    public void setCustomerServiceRequest(CustomerServiceRequest customerServiceRequest) {
        this.customerServiceRequest = customerServiceRequest;
    }
}
