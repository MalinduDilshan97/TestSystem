package com.spring.starter.DTO;

import java.io.Serializable;

public class BranchDTO implements Serializable {

    private int branch_id;
    private int mx_branch_code;
    private String mx_branch_name;
    private boolean ceft;
    private int mx_bank_code;

    public BranchDTO() {
    }

    public BranchDTO(int branch_id, int mx_branch_code, String mx_branch_name, boolean ceft, int mx_bank_code) {
        this.branch_id = branch_id;
        this.mx_branch_code = mx_branch_code;
        this.mx_branch_name = mx_branch_name;
        this.ceft = ceft;
        this.mx_bank_code = mx_bank_code;
    }

    public int getBranch_id() {
        return branch_id;
    }

    public void setBranch_id(int branch_id) {
        this.branch_id = branch_id;
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

    public int getMx_bank_code() {
        return mx_bank_code;
    }

    public void setMx_bank_code(int mx_bank_code) {
        this.mx_bank_code = mx_bank_code;
    }
}
