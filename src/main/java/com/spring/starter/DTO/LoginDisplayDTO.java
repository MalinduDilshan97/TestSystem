package com.spring.starter.DTO;

import com.spring.starter.model.Branch;

public class LoginDisplayDTO {

    private String authToken;

    private Branch branch;

    private String clientKey;

    private String browserKey;

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

    public String getClientKey() {
        return clientKey;
    }

    public void setClientKey(String clientKey) {
        this.clientKey = clientKey;
    }

    public String getBrowserKey() {
        return browserKey;
    }

    public void setBrowserKey(String browserKey) {
        this.browserKey = browserKey;
    }
}
