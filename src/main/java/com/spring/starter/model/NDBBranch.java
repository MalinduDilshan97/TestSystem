package com.spring.starter.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;

import org.springframework.lang.NonNull;

@Entity
public class NDBBranch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NonNull
    @NotEmpty
    private String branch_name;
    @NonNull
    @NotEmpty
    private String branchUid;
    @NonNull
    @NotEmpty
    private String branchAddress;
    @NonNull
    @NotEmpty
    private String branchManeger;
    @NonNull
    @NotEmpty
    private String branchTel;
    @NonNull
    @NotEmpty
    private String branchFax;

    public NDBBranch() {
        super();
    }

    public NDBBranch(int id, @NotEmpty String branch_name, @NotEmpty String branchUid, @NotEmpty String branchAddress,
                     @NotEmpty String branchManeger, @NotEmpty String branchTel, @NotEmpty String branchFax) {
        super();
        this.id = id;
        this.branch_name = branch_name;
        this.branchUid = branchUid;
        this.branchAddress = branchAddress;
        this.branchManeger = branchManeger;
        this.branchTel = branchTel;
        this.branchFax = branchFax;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBranch_name() {
        return branch_name;
    }

    public void setBranch_name(String branch_name) {
        this.branch_name = branch_name;
    }

    public String getBranchUid() {
        return branchUid;
    }

    public void setBranchUid(String branchUid) {
        this.branchUid = branchUid;
    }

    public String getBranchAddress() {
        return branchAddress;
    }

    public void setBranchAddress(String branchAddress) {
        this.branchAddress = branchAddress;
    }

    public String getBranchManeger() {
        return branchManeger;
    }

    public void setBranchManeger(String branchManeger) {
        this.branchManeger = branchManeger;
    }

    public String getBranchTel() {
        return branchTel;
    }

    public void setBranchTel(String branchTel) {
        this.branchTel = branchTel;
    }

    public String getBranchFax() {
        return branchFax;
    }

    public void setBranchFax(String branchFax) {
        this.branchFax = branchFax;
    }
}
