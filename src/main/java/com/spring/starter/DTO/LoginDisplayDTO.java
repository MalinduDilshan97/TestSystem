package com.spring.starter.DTO;

import com.spring.starter.model.Branch;

public class LoginDisplayDTO {

    private String authToken;

    private Branch branch;

    public LoginDisplayDTO(String authToken, Branch branch) {
        this.authToken = authToken;
        this.branch = branch;
    }

    public LoginDisplayDTO() {
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public Branch getBranch() {
        return branch;
    }

    public void setBranch(Branch branch) {
        this.branch = branch;
    }
}
