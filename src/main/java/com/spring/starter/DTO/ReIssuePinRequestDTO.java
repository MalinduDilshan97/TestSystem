package com.spring.starter.DTO;


public class ReIssuePinRequestDTO {

    private int reIssuePinRequestId;
    private int branch;
    private String address;
    private boolean avBranch= false;
    private boolean avAddress= false;
    private boolean avCurrespondingAddress = false;
    private int customerServiceRequestId;

    public ReIssuePinRequestDTO() {
    }

    public ReIssuePinRequestDTO(int reIssuePinRequestId, int branch, String address, boolean avBranch, boolean avAddress, boolean avCurrespondingAddress, int customerServiceRequestId) {
        this.reIssuePinRequestId = reIssuePinRequestId;
        this.branch = branch;
        this.address = address;
        this.avBranch = avBranch;
        this.avAddress = avAddress;
        this.avCurrespondingAddress = avCurrespondingAddress;
        this.customerServiceRequestId = customerServiceRequestId;
    }

    public int getReIssuePinRequestId() {
        return reIssuePinRequestId;
    }

    public void setReIssuePinRequestId(int reIssuePinRequestId) {
        this.reIssuePinRequestId = reIssuePinRequestId;
    }

    public int getBranch() {
        return branch;
    }

    public void setBranch(int branch) {
        this.branch = branch;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean isAvBranch() {
        return avBranch;
    }

    public void setAvBranch(boolean avBranch) {
        this.avBranch = avBranch;
    }

    public boolean isAvAddress() {
        return avAddress;
    }

    public void setAvAddress(boolean avAddress) {
        this.avAddress = avAddress;
    }

    public boolean isAvCurrespondingAddress() {
        return avCurrespondingAddress;
    }

    public void setAvCurrespondingAddress(boolean avCurrespondingAddress) {
        this.avCurrespondingAddress = avCurrespondingAddress;
    }

    public int getCustomerServiceRequestId() {
        return customerServiceRequestId;
    }

    public void setCustomerServiceRequestId(int customerServiceRequestId) {
        this.customerServiceRequestId = customerServiceRequestId;
    }
}