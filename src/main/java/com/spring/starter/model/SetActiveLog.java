package com.spring.starter.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.lang.NonNull;

@Entity
@Table(name = "set_active_log")
public class SetActiveLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int activeId;
    @ManyToOne
    @JoinColumn(name = "staffId")
    private StaffUser activeBy;
    @ManyToOne
    @JoinColumn(name = "activeWhoID")
    private StaffUser activeWho;
    @NonNull
    private Date activeDate;
    @NonNull
    private String activeIp;
    @NonNull
    private String message;

    public SetActiveLog() {
        super();
    }

    public SetActiveLog(int activeId, StaffUser activeBy, StaffUser activeWho, Date activeDate, String activeIp) {
        super();
        this.activeId = activeId;
        this.activeBy = activeBy;
        this.activeWho = activeWho;
        this.activeDate = activeDate;
        this.activeIp = activeIp;
    }


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getActiveId() {
        return activeId;
    }

    public void setActiveId(int activeId) {
        this.activeId = activeId;
    }

    public StaffUser getActiveBy() {
        return activeBy;
    }

    public void setActiveBy(StaffUser activeBy) {
        this.activeBy = activeBy;
    }

    public StaffUser getActiveWho() {
        return activeWho;
    }

    public void setActiveWho(StaffUser activeWho) {
        this.activeWho = activeWho;
    }

    public Date getActiveDate() {
        return activeDate;
    }

    public void setActiveDate(Date activeDate) {
        this.activeDate = activeDate;
    }

    public String getActiveIp() {
        return activeIp;
    }

    public void setActiveIp(String activeIp) {
        this.activeIp = activeIp;
    }

}
