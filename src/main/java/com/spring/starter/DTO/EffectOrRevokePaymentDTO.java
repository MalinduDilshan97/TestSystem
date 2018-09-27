package com.spring.starter.DTO;

import java.io.Serializable;
import java.util.List;

public class EffectOrRevokePaymentDTO implements Serializable {

    private int EffectOrRevokePaymentId;
    private String status;
    private String accountNo;
    private int customerServiceRequestId;
    private List<EffectOrRevokePaymentDetailsDTO> list;

    public EffectOrRevokePaymentDTO() {
    }

    public EffectOrRevokePaymentDTO(String status, String accountNo, int customerServiceRequestId, List<EffectOrRevokePaymentDetailsDTO> list) {
        this.status = status;
        this.accountNo = accountNo;
        this.customerServiceRequestId = customerServiceRequestId;
        this.list = list;
    }

    public EffectOrRevokePaymentDTO(int effectOrRevokePaymentId, String status, String accountNo, int customerServiceRequestId, List<EffectOrRevokePaymentDetailsDTO> list) {
        EffectOrRevokePaymentId = effectOrRevokePaymentId;
        this.status = status;
        this.accountNo = accountNo;
        this.customerServiceRequestId = customerServiceRequestId;
        this.list = list;
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

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public int getCustomerServiceRequestId() {
        return customerServiceRequestId;
    }

    public void setCustomerServiceRequestId(int customerServiceRequestId) {
        this.customerServiceRequestId = customerServiceRequestId;
    }

    public List<EffectOrRevokePaymentDetailsDTO> getList() {
        return list;
    }

    public void setList(List<EffectOrRevokePaymentDetailsDTO> list) {
        this.list = list;
    }
}
