package com.spring.starter.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Table(name = "branch")
public class Branch implements Serializable {

    @Id
    private int mx_branch_code;
    @NotNull
    private String mx_branch_name;
    @NotNull
    private boolean ceft;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bank_id")
    @JsonIgnore
    private Bank bank;

    public Branch() {
    }

    public Branch(int mx_branch_code, String mx_branch_name, boolean ceft, Bank bank) {
        this.mx_branch_code = mx_branch_code;
        this.mx_branch_name = mx_branch_name;
        this.ceft = ceft;
        this.bank = bank;
    }


    public int getMx_branch_code() {
        return mx_branch_code;
    }

    public void setMx_branch_code(int mx_branch_code) {
        this.mx_branch_code = mx_branch_code;
    }

    public String getMx_branch_name() {
        return mx_branch_name;
    }

    public void setMx_branch_name(String mx_branch_name) {
        this.mx_branch_name = mx_branch_name;
    }

    public boolean isCeft() {
        return ceft;
    }

    public void setCeft(boolean ceft) {
        this.ceft = ceft;
    }

    public Bank getBank() {
        return bank;
    }

    public void setBank(Bank bank) {
        this.bank = bank;
    }
}