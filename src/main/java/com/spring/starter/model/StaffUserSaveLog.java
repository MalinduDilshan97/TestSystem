package com.spring.starter.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "staff_user_save_log")
public class StaffUserSaveLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int staffUserSaveLogId;

    @NotNull
    private Date date;

    @NotNull
    private String savedIp;

    @ManyToOne
    @JoinColumn(name = "savedById")
    private StaffUser savedBy;

    @OneToOne
    @JoinColumn(name = "savedWhoId")
    private StaffUser savedWho;

    @NotNull
    private String message;

    public StaffUserSaveLog(int staffUserSaveLogId, @NotNull Date date, @NotNull String savedIp, StaffUser savedBy,
                            StaffUser savedWho, @NotNull String message) {
        super();
        this.staffUserSaveLogId = staffUserSaveLogId;
        this.date = date;
        this.savedIp = savedIp;
        this.savedBy = savedBy;
        this.savedWho = savedWho;
        this.message = message;
    }

    public StaffUserSaveLog() {
        super();
    }

    public int getStaffUserSaveLogId() {
        return staffUserSaveLogId;
    }

    public void setStaffUserSaveLogId(int staffUserSaveLogId) {
        this.staffUserSaveLogId = staffUserSaveLogId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getSavedIp() {
        return savedIp;
    }

    public void setSavedIp(String savedIp) {
        this.savedIp = savedIp;
    }

    public StaffUser getSavedBy() {
        return savedBy;
    }

    public void setSavedBy(StaffUser savedBy) {
        this.savedBy = savedBy;
    }

    public StaffUser getSavedWho() {
        return savedWho;
    }

    public void setSavedWho(StaffUser savedWho) {
        this.savedWho = savedWho;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
