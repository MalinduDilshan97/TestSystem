package com.spring.starter.model;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Date;

@Entity
@Table(name = "transaction_customer")
public class TransactionCustomer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int transactionCustomerId;

    @NotNull
    @Size(min = 2)
    @Pattern(regexp = "^([A-Za-z0-9_\\s])*$")
    private String name;

    @NotNull
    @NotEmpty
    private String identification;

    @NotNull
    @NotEmpty
    @Size(min = 9, max = 10,message = "mobile number must be between 9 and 10")
    @Pattern(regexp = "^([+0-9])*$")
    private String mobile;

    @Column
    private Date date;

    public TransactionCustomer() {
    }

    public TransactionCustomer(@NotNull @Size(min = 2) @Pattern(regexp = "^([A-Za-z0-9_\\s])*$") String name, @NotNull @NotEmpty String identification, @NotNull @NotEmpty @Size(min = 9, max = 10, message = "mobile number must be between 9 and 10") @Pattern(regexp = "^([+0-9])*$") String mobile, Date date) {
        this.name = name;
        this.identification = identification;
        this.mobile = mobile;
        this.date = date;
    }

    public TransactionCustomer(@NotNull @Size(min = 2) @Pattern(regexp = "^([A-Za-z0-9_\\s])*$") String name, @NotNull
    @NotEmpty String identification, @NotNull @NotEmpty @Min(9) @Max(10) @Pattern(regexp = "^([+0-9])*$") String mobile)
    {
        this.name = name;
        this.identification = identification;
        this.mobile = mobile;
    }

    public TransactionCustomer(@NotNull @NotEmpty String identification,
                               @NotNull @NotEmpty @Pattern(regexp = "^([+0-9])*$") String mobile) {
        this.identification = identification;
        this.mobile = mobile;
    }

    public int getTransactionCustomerId() {
        return transactionCustomerId;
    }

    public void setTransactionCustomerId(int transactionCustomerId) {
        this.transactionCustomerId = transactionCustomerId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdentification() {
        return identification;
    }

    public void setIdentification(String identification) {
        this.identification = identification;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
