package com.spring.starter.model;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "statement_frequency")
public class StatementFrequency {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int statementFrequency;

    @NotEmpty
    @NotNull
    private String accountNo;

    private boolean daily = false;

    private boolean quarterly = false;

    private boolean annually = false;

    private boolean monthly = false;

    private boolean biAnnaully = false;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "csrId")
    private CustomerServiceRequest customerServiceRequest;

    public StatementFrequency() {
    }

    public StatementFrequency(@NotEmpty @NotNull String accountNo, boolean daily, boolean quarterly, boolean annually, boolean monthly, boolean biAnnaully, CustomerServiceRequest customerServiceRequest) {
        this.accountNo = accountNo;
        this.daily = daily;
        this.quarterly = quarterly;
        this.annually = annually;
        this.monthly = monthly;
        this.biAnnaully = biAnnaully;
        this.customerServiceRequest = customerServiceRequest;
    }

    public int getStatementFrequency() {
        return statementFrequency;
    }

    public void setStatementFrequency(int statementFrequency) {
        this.statementFrequency = statementFrequency;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public boolean isDaily() {
        return daily;
    }

    public void setDaily(boolean daily) {
        this.daily = daily;
    }

    public boolean isQuarterly() {
        return quarterly;
    }

    public void setQuarterly(boolean quarterly) {
        this.quarterly = quarterly;
    }

    public boolean isAnnually() {
        return annually;
    }

    public void setAnnually(boolean annually) {
        this.annually = annually;
    }

    public boolean isMonthly() {
        return monthly;
    }

    public void setMonthly(boolean monthly) {
        this.monthly = monthly;
    }

    public boolean isBiAnnaully() {
        return biAnnaully;
    }

    public void setBiAnnaully(boolean biAnnaully) {
        this.biAnnaully = biAnnaully;
    }

    public CustomerServiceRequest getCustomerServiceRequest() {
        return customerServiceRequest;
    }

    public void setCustomerServiceRequest(CustomerServiceRequest customerServiceRequest) {
        this.customerServiceRequest = customerServiceRequest;
    }
}
