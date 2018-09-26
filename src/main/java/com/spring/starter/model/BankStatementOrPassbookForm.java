package com.spring.starter.model;

import java.util.Date;
import java.util.List;

import javax.persistence.*;

import com.spring.starter.enums.BankStatementFrequency;
import com.spring.starter.enums.NatureOfStatement;

@Entity
@Table(name = "bank_statement_or_passbook_form")
public class BankStatementOrPassbookForm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int BankStatementOrPassbookFormId;

    private boolean issueAccountStatementForTheBelowPeriod;
    private Date fromDate;
    private Date toDate;

    @Enumerated(EnumType.STRING)
    private NatureOfStatement natureOfStatement;

    private boolean passbookOrDuplicatePassbookRequest;


    private boolean activeOrCancelEStatementFacility;

    @OneToMany
    @JoinColumn(name = "BankStatementOrPassbookFormId")
    private List<BankStatementOrPassbookFormCustomerAccountNos> accountNos;

    private String email;

    private boolean changeTheFrquency;

    @Enumerated(EnumType.STRING)
    private BankStatementFrequency bankStatementFrequency;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "csrId")
    private CustomerServiceRequest customerServiceRequest;

    public BankStatementOrPassbookForm() {
        super();
    }

    public BankStatementOrPassbookForm(boolean issueAccountStatementForTheBelowPeriod, Date fromDate, Date toDate, NatureOfStatement natureOfStatement, boolean passbookOrDuplicatePassbookRequest, boolean activeOrCancelEStatementFacility, List<BankStatementOrPassbookFormCustomerAccountNos> accountNos, String email, boolean changeTheFrquency, BankStatementFrequency bankStatementFrequency, CustomerServiceRequest customerServiceRequest) {
        this.issueAccountStatementForTheBelowPeriod = issueAccountStatementForTheBelowPeriod;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.natureOfStatement = natureOfStatement;
        this.passbookOrDuplicatePassbookRequest = passbookOrDuplicatePassbookRequest;
        this.activeOrCancelEStatementFacility = activeOrCancelEStatementFacility;
        this.accountNos = accountNos;
        this.email = email;
        this.changeTheFrquency = changeTheFrquency;
        this.bankStatementFrequency = bankStatementFrequency;
        this.customerServiceRequest = customerServiceRequest;
    }

    public int getBankStatementOrPassbookFormId() {
        return BankStatementOrPassbookFormId;
    }

    public void setBankStatementOrPassbookFormId(int bankStatementOrPassbookFormId) {
        BankStatementOrPassbookFormId = bankStatementOrPassbookFormId;
    }

    public boolean isIssueAccountStatementForTheBelowPeriod() {
        return issueAccountStatementForTheBelowPeriod;
    }

    public void setIssueAccountStatementForTheBelowPeriod(boolean issueAccountStatementForTheBelowPeriod) {
        this.issueAccountStatementForTheBelowPeriod = issueAccountStatementForTheBelowPeriod;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    public NatureOfStatement getNatureOfStatement() {
        return natureOfStatement;
    }

    public void setNatureOfStatement(NatureOfStatement natureOfStatement) {
        this.natureOfStatement = natureOfStatement;
    }

    public boolean isPassbookOrDuplicatePassbookRequest() {
        return passbookOrDuplicatePassbookRequest;
    }

    public void setPassbookOrDuplicatePassbookRequest(boolean passbookOrDuplicatePassbookRequest) {
        this.passbookOrDuplicatePassbookRequest = passbookOrDuplicatePassbookRequest;
    }

    public boolean isActiveOrCancelEStatementFacility() {
        return activeOrCancelEStatementFacility;
    }

    public void setActiveOrCancelEStatementFacility(boolean activeOrCancelEStatementFacility) {
        this.activeOrCancelEStatementFacility = activeOrCancelEStatementFacility;
    }

    public List<BankStatementOrPassbookFormCustomerAccountNos> getAccountNos() {
        return accountNos;
    }

    public void setAccountNos(List<BankStatementOrPassbookFormCustomerAccountNos> accountNos) {
        this.accountNos = accountNos;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isChangeTheFrquency() {
        return changeTheFrquency;
    }

    public void setChangeTheFrquency(boolean changeTheFrquency) {
        this.changeTheFrquency = changeTheFrquency;
    }

    public BankStatementFrequency getBankStatementFrequency() {
        return bankStatementFrequency;
    }

    public void setBankStatementFrequency(BankStatementFrequency bankStatementFrequency) {
        this.bankStatementFrequency = bankStatementFrequency;
    }

    public CustomerServiceRequest getCustomerServiceRequest() {
        return customerServiceRequest;
    }

    public void setCustomerServiceRequest(CustomerServiceRequest customerServiceRequest) {
        this.customerServiceRequest = customerServiceRequest;
    }

}
