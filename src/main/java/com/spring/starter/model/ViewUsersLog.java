package com.spring.starter.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "view_users_log")
public class ViewUsersLog {

    @Id
    @GeneratedValue
    private int viewUsersLogId;
    @NotNull
    private String viewedItem;
    @NotNull
    private Date viwedTime;
    @NotNull
    private String viewedIp;

    public ViewUsersLog() {
        super();
    }

    public ViewUsersLog(int viewUsersLogId, @NotNull String viewedItem, @NotNull Date viwedTime,
                        @NotNull String viewedIp) {
        super();
        this.viewUsersLogId = viewUsersLogId;
        this.viewedItem = viewedItem;
        this.viwedTime = viwedTime;
        this.viewedIp = viewedIp;
    }

    public int getViewUsersLogId() {
        return viewUsersLogId;
    }

    public void setViewUsersLogId(int viewUsersLogId) {
        this.viewUsersLogId = viewUsersLogId;
    }

    public String getViewedItem() {
        return viewedItem;
    }

    public void setViewedItem(String viewedItem) {
        this.viewedItem = viewedItem;
    }

    public Date getViwedTime() {
        return viwedTime;
    }

    public void setViwedTime(Date viwedTime) {
        this.viwedTime = viwedTime;
    }

    public String getViewedIp() {
        return viewedIp;
    }

    public void setViewedIp(String viewedIp) {
        this.viewedIp = viewedIp;
    }
}
