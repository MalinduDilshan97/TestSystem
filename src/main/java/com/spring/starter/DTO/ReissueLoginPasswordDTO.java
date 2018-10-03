package com.spring.starter.DTO;

import javax.persistence.FetchType;

import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.spring.starter.model.CustomerServiceRequest;


public class ReissueLoginPasswordDTO {

    @NotNull
    @NotEmpty
    private String bankingUserId;

    private boolean atBranch = false;

    private int branchId;

    private boolean postToCorrespondenceAddress = false;

    private boolean postToAddress = false;

    private String addresss;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "csrId")
    private CustomerServiceRequest customerServiceRequest;

    public ReissueLoginPasswordDTO() {
        super();
    }

    public ReissueLoginPasswordDTO(@NotNull @NotEmpty String bankingUserId, boolean atBranch, int branchId,
                                   boolean postToCorrespondenceAddress, boolean postToAddress, String addresss,
                                   CustomerServiceRequest customerServiceRequest) {
        super();
        this.bankingUserId = bankingUserId;
        this.atBranch = atBranch;
        this.branchId = branchId;
        this.postToCorrespondenceAddress = postToCorrespondenceAddress;
        this.postToAddress = postToAddress;
        this.addresss = addresss;
        this.customerServiceRequest = customerServiceRequest;
    }


    public ReissueLoginPasswordDTO(@NotNull @NotEmpty String bankingUserId, boolean atBranch, int branchId,
                                   boolean postToCorrespondenceAddress, String addresss, CustomerServiceRequest customerServiceRequest) {
        super();
        this.bankingUserId = bankingUserId;
        this.atBranch = atBranch;
        this.branchId = branchId;
        this.postToCorrespondenceAddress = postToCorrespondenceAddress;
        this.addresss = addresss;
        this.customerServiceRequest = customerServiceRequest;
    }

    public String getBankingUserId() {
        return bankingUserId;
    }

    public void setBankingUserId(String bankingUserId) {
        this.bankingUserId = bankingUserId;
    }

    public boolean isAtBranch() {
        return atBranch;
    }

    public void setAtBranch(boolean atBranch) {
        this.atBranch = atBranch;
    }

    public int getBranchId() {
        return branchId;
    }

    public void setBranchId(int branchId) {
        this.branchId = branchId;
    }

    public boolean isPostToCorrespondenceAddress() {
        return postToCorrespondenceAddress;
    }

    public void setPostToCorrespondenceAddress(boolean postToCorrespondenceAddress) {
        this.postToCorrespondenceAddress = postToCorrespondenceAddress;
    }

    public String getAddresss() {
        return addresss;
    }

    public void setAddresss(String addresss) {
        this.addresss = addresss;
    }

    public CustomerServiceRequest getCustomerServiceRequest() {
        return customerServiceRequest;
    }

    public void setCustomerServiceRequest(CustomerServiceRequest customerServiceRequest) {
        this.customerServiceRequest = customerServiceRequest;
    }

    public boolean isPostToAddress() {
        return postToAddress;
    }

    public void setPostToAddress(boolean postToAddress) {
        this.postToAddress = postToAddress;
    }

}
