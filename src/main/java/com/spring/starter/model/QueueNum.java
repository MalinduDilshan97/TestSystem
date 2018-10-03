package com.spring.starter.model;


import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

import org.springframework.lang.NonNull;

@Entity
public class QueueNum {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int queueNumId;

    @GeneratedValue
    private int queueIssueNumber;

    private Date queueDate;

    @NonNull
    @NotBlank
    @NotEmpty
    private String name;

    @NonNull
    @NotBlank
    @NotEmpty
    private String mobileNo;

    public QueueNum(int queueNumId, int queueIssueNumber, Date queueDate, @NotBlank @NotEmpty String name,
                    @NotBlank @NotEmpty String mobileNo) {
        super();
        this.queueNumId = queueNumId;
        this.queueIssueNumber = queueIssueNumber;
        this.queueDate = queueDate;
        this.name = name;
        this.mobileNo = mobileNo;
    }

    public int getQueueNumId() {
        return queueNumId;
    }

    public void setQueueNumId(int queueNumId) {
        this.queueNumId = queueNumId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public QueueNum(int queueNumId, @NotBlank @NotEmpty String name, @NotBlank @NotEmpty String mobileNo) {
        super();
        this.queueNumId = queueNumId;
        this.name = name;
        this.mobileNo = mobileNo;
    }

    public QueueNum() {
        super();
    }

    public int getQueueIssueNumber() {
        return queueIssueNumber;
    }

    public void setQueueIssueNumber(int queueIssueNumber) {
        this.queueIssueNumber = queueIssueNumber;
    }

    public Date getQueueDate() {
        return queueDate;
    }

    public void setQueueDate(Date queueDate) {
        this.queueDate = queueDate;
    }

}
