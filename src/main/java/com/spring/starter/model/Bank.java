package com.spring.starter.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "bank")
public class Bank implements Serializable {

    @Id
    private int mx_bank_code;
    @NotNull
    private String mx_bank_name;
    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "bank_id")
    @JsonIgnore
    private List<Branch> branchList;

    public Bank() {
    }

    public Bank(int mx_bank_code, String mx_bank_name, List<Branch> branchList) {
        this.mx_bank_code = mx_bank_code;
        this.mx_bank_name = mx_bank_name;
        this.branchList = branchList;
    }



    public int getMx_bank_code() {
        return mx_bank_code;
    }

    public void setMx_bank_code(int mx_bank_code) {
        this.mx_bank_code = mx_bank_code;
    }

    public String getMx_bank_name() {
        return mx_bank_name;
    }

    public void setMx_bank_name(String mx_bank_name) {
        this.mx_bank_name = mx_bank_name;
    }

    public List<Branch> getBranchList() {
        return branchList;
    }

    public void setBranchList(List<Branch> branchList) {
        this.branchList = branchList;
    }
}
