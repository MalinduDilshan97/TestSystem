package com.spring.starter.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "mailing_mail_account_numbers")
public class MailingMailAccountNumbers {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int mailingMailAccountNumbersId;

    @NotNull
    private String accountNo;

    public MailingMailAccountNumbers() {
        super();
    }

    public MailingMailAccountNumbers(int mailingMailAccountNumbersId, @NotNull String accountNo) {
        super();
        this.mailingMailAccountNumbersId = mailingMailAccountNumbersId;
        this.accountNo = accountNo;
    }

    public int getMailingMailAccountNumbersId() {
        return mailingMailAccountNumbersId;
    }

    public void setMailingMailAccountNumbersId(int mailingMailAccountNumbersId) {
        this.mailingMailAccountNumbersId = mailingMailAccountNumbersId;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

}
