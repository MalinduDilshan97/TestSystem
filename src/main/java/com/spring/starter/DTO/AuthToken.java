package com.spring.starter.DTO;

public class AuthToken {

    String accessToken;

    public AuthToken() {
        super();
    }

    public AuthToken(String accessToken) {
        super();
        this.accessToken = accessToken;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

}
