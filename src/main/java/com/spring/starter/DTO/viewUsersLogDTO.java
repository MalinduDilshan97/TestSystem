package com.spring.starter.DTO;

import java.util.Date;

import com.spring.starter.model.StaffUser;

public class viewUsersLogDTO {

    private StaffUser viewUser;
    private String viewedItem;
    private Date viewedTime;
    private String viewedIp;

    public viewUsersLogDTO() {
        super();
    }

    public StaffUser getViewUser() {
        return viewUser;
    }

    public void setViewUser(StaffUser viewUser) {
        this.viewUser = viewUser;
    }

    public String getViewedItem() {
        return viewedItem;
    }

    public void setViewedItem(String viewedItem) {
        this.viewedItem = viewedItem;
    }

    public Date getViewedTime() {
        return viewedTime;
    }

    public void setViewedTime(Date viewedTime) {
        this.viewedTime = viewedTime;
    }

    public String getViewedIp() {
        return viewedIp;
    }

    public void setViewedIp(String viewedIp) {
        this.viewedIp = viewedIp;
    }

}
