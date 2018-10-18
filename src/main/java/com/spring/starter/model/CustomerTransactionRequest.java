package com.spring.starter.model;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="customer_transaction_request")
public class CustomerTransactionRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int customerTransactionRequestId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="transactionRequestId")
    private TransactionRequest transactionRequest;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="transactionCustomerId")
    private TransactionCustomer transactionCustomer;

    private boolean status = false;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name="customerTransactionRequestId")
    private List<StaffUser> staffUser;

    private Date requestDate;

    private Date requestCompleteDate;

    public CustomerTransactionRequest() {
    }

    public CustomerTransactionRequest(TransactionRequest transactionRequest, TransactionCustomer transactionCustomer,
                                      boolean status, List<StaffUser> staffUser, Date requestDate) {
        this.transactionRequest = transactionRequest;
        this.transactionCustomer = transactionCustomer;
        this.status = status;
        this.staffUser = staffUser;
        this.requestDate = requestDate;
    }

    public int getCustomerTransactionRequestId() {
        return customerTransactionRequestId;
    }

    public void setCustomerTransactionRequestId(int customerTransactionRequestId) {
        this.customerTransactionRequestId = customerTransactionRequestId;
    }

    public TransactionRequest getTransactionRequest() {
        return transactionRequest;
    }

    public void setTransactionRequest(TransactionRequest transactionRequest) {
        this.transactionRequest = transactionRequest;
    }

    public TransactionCustomer getTransactionCustomer() {
        return transactionCustomer;
    }

    public void setTransactionCustomer(TransactionCustomer transactionCustomer) {
        this.transactionCustomer = transactionCustomer;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public List<StaffUser> getStaffUser() {
        return staffUser;
    }

    public void setStaffUser(List<StaffUser> staffUser) {
        this.staffUser = staffUser;
    }

    public Date getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(Date requestDate) {
        this.requestDate = requestDate;
    }

    public Date getRequestCompleteDate() {
        return requestCompleteDate;
    }

    public void setRequestCompleteDate(Date requestCompleteDate) {
        this.requestCompleteDate = requestCompleteDate;
    }
}
