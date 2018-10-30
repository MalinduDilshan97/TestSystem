package com.spring.starter.DTO;

public class ChannelCreateDTO {

    private int staffId;

    private String clientKey;

    private String browserKey;

    public ChannelCreateDTO() {
    }

    public ChannelCreateDTO(int staffId, String clientKey, String browserKey) {
        this.staffId = staffId;
        this.clientKey = clientKey;
        this.browserKey = browserKey;
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

    public int getStaffId() {
        return staffId;
    }

    public void setStaffId(int staffId) {
        this.staffId = staffId;
    }
}
