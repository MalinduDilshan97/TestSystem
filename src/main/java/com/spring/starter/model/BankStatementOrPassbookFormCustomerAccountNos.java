package com.spring.starter.model;

import javax.persistence.*;

@Entity
@Table(name = "bank_statement_or_passbook_form_customer_account_nos")
public class BankStatementOrPassbookFormCustomerAccountNos {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private int accountNoid;
    @Column(nullable = false)
    private String accountNo;



}
