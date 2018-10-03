package com.spring.starter.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "bank_statement_accountno")
public class BankStatementAccountNo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int bankStatementAccountNoId;

    @NotNull
    private String accountNo;

    public BankStatementAccountNo(@NotNull String accountNo) {
        this.accountNo = accountNo;
    }

    public BankStatementAccountNo() {
    }

    public int getBankStatementAccountNoId() {
        return bankStatementAccountNoId;
    }

    public void setBankStatementAccountNoId(int bankStatementAccountNoId) {
        this.bankStatementAccountNoId = bankStatementAccountNoId;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }
}
