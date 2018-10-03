package com.spring.starter.DTO;

import java.util.List;

import com.spring.starter.model.NDBBranch;

public class InternetBankingDTO {


    private String existingBankingUserId;

    private boolean reissueALoginPassword;

    private boolean atbranch;

    private boolean correspondanceAddress;

    private int branchId;

    private boolean postToAddress;

    private String address;

    private boolean linkjointAccounts;

    private List<String> internetBankingLinkAccountNumbers;

    private boolean excludeAccounts;

    private List<String> bankingExcludeAccountNumbers;

    private boolean cancelInternetbanking;

    private boolean activeUser;

    private boolean inactiveUser;

    public InternetBankingDTO() {
        super();
    }

    public InternetBankingDTO(String existingBankingUserId, boolean reissueALoginPassword, boolean atbranch,
                              boolean correspondanceAddress, int branchId, boolean postToAddress, String address,
                              boolean linkjointAccounts, List<String> internetBankingLinkAccountNumbers, boolean excludeAccounts,
                              List<String> bankingExcludeAccountNumbers, boolean cancelInternetbanking, boolean activeUser,
                              boolean inactiveUser) {
        super();
        this.existingBankingUserId = existingBankingUserId;
        this.reissueALoginPassword = reissueALoginPassword;
        this.atbranch = atbranch;
        this.correspondanceAddress = correspondanceAddress;
        this.branchId = branchId;
        this.postToAddress = postToAddress;
        this.address = address;
        this.linkjointAccounts = linkjointAccounts;
        this.internetBankingLinkAccountNumbers = internetBankingLinkAccountNumbers;
        this.excludeAccounts = excludeAccounts;
        this.bankingExcludeAccountNumbers = bankingExcludeAccountNumbers;
        this.cancelInternetbanking = cancelInternetbanking;
        this.activeUser = activeUser;
        this.inactiveUser = inactiveUser;
    }

    public String getExistingBankingUserId() {
        return existingBankingUserId;
    }

    public void setExistingBankingUserId(String existingBankingUserId) {
        this.existingBankingUserId = existingBankingUserId;
    }

    public boolean isReissueALoginPassword() {
        return reissueALoginPassword;
    }

    public void setReissueALoginPassword(boolean reissueALoginPassword) {
        this.reissueALoginPassword = reissueALoginPassword;
    }

    public boolean isAtbranch() {
        return atbranch;
    }

    public void setAtbranch(boolean atbranch) {
        this.atbranch = atbranch;
    }

    public int getBranchId() {
        return branchId;
    }

    public void setBranchId(int branchId) {
        this.branchId = branchId;
    }

    public boolean isPostToAddress() {
        return postToAddress;
    }

    public void setPostToAddress(boolean postToAddress) {
        this.postToAddress = postToAddress;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean isLinkjointAccounts() {
        return linkjointAccounts;
    }

    public void setLinkjointAccounts(boolean linkjointAccounts) {
        this.linkjointAccounts = linkjointAccounts;
    }

    public List<String> getInternetBankingLinkAccountNumbers() {
        return internetBankingLinkAccountNumbers;
    }

    public void setInternetBankingLinkAccountNumbers(List<String> internetBankingLinkAccountNumbers) {
        this.internetBankingLinkAccountNumbers = internetBankingLinkAccountNumbers;
    }

    public boolean isExcludeAccounts() {
        return excludeAccounts;
    }

    public void setExcludeAccounts(boolean excludeAccounts) {
        this.excludeAccounts = excludeAccounts;
    }

    public List<String> getBankingExcludeAccountNumbers() {
        return bankingExcludeAccountNumbers;
    }

    public void setBankingExcludeAccountNumbers(List<String> bankingExcludeAccountNumbers) {
        this.bankingExcludeAccountNumbers = bankingExcludeAccountNumbers;
    }

    public boolean isCorrespondanceAddress() {
        return correspondanceAddress;
    }

    public void setCorrespondanceAddress(boolean correspondanceAddress) {
        this.correspondanceAddress = correspondanceAddress;
    }

    public boolean isCancelInternetbanking() {
        return cancelInternetbanking;
    }

    public void setCancelInternetbanking(boolean cancelInternetbanking) {
        this.cancelInternetbanking = cancelInternetbanking;
    }

    public boolean isActiveUser() {
        return activeUser;
    }

    public void setActiveUser(boolean activeUser) {
        this.activeUser = activeUser;
    }

    public boolean isInactiveUser() {
        return inactiveUser;
    }

    public void setInactiveUser(boolean inactiveUser) {
        this.inactiveUser = inactiveUser;
    }

}
