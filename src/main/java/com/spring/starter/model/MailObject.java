package com.spring.starter.model;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class MailObject {

    @Email
    @NotNull
    @Size(min = 1, message = "Please, set an email address to send the message to it")
    private String from;

    @Email
    @NotNull
    @Size(min = 1, message = "Please, set an email address to send the message to it")
    private String to;

    private String subject;
    private String content;

    public MailObject() {
    }

    public MailObject(@Email @NotNull @Size(min = 1, message = "Please, set an email address to send the message to it") String from, @Email @NotNull @Size(min = 1, message = "Please, set an email address to send the message to it") String to, String subject, String content) {
        this.from = from;
        this.to = to;
        this.subject = subject;
        this.content = content;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}