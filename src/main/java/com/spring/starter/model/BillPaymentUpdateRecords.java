package com.spring.starter.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.List;

@Entity
@Table(name ="bill_payment_update_records")
public class BillPaymentUpdateRecords {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int billPaymentUpdateRecordsId;

    @NotNull
    @Pattern(regexp = "^([A-Za-z0-9_\\s])*$")
    private String comment;

    private String url;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "billpayment_update_error_id")
    private List<BillPaymentErrorRecords> billPaymentErrorRecords;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_service_request_id")
    private CustomerTransactionRequest customerTransactionRequest;

    public BillPaymentUpdateRecords() {
    }

    public BillPaymentUpdateRecords(@NotNull @Pattern(regexp = "^([A-Za-z0-9_\\s])*$") String comment, String url,
                                    List<BillPaymentErrorRecords> billPaymentErrorRecords,
                                    CustomerTransactionRequest customerTransactionRequest) {
        this.comment = comment;
        this.url = url;
        this.billPaymentErrorRecords = billPaymentErrorRecords;
        this.customerTransactionRequest = customerTransactionRequest;
    }

    public BillPaymentUpdateRecords(@NotNull @Pattern(regexp = "^([A-Za-z0-9_\\s])*$") String comment,
                                    List<BillPaymentErrorRecords> billPaymentErrorRecords,
                                    CustomerTransactionRequest customerTransactionRequest) {
        this.comment = comment;
        this.billPaymentErrorRecords = billPaymentErrorRecords;
        this.customerTransactionRequest = customerTransactionRequest;
    }

    public int getBillPaymentUpdateRecordsId() {
        return billPaymentUpdateRecordsId;
    }

    public void setBillPaymentUpdateRecordsId(int billPaymentUpdateRecordsId) {
        this.billPaymentUpdateRecordsId = billPaymentUpdateRecordsId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public List<BillPaymentErrorRecords> getBillPaymentErrorRecords() {
        return billPaymentErrorRecords;
    }

    public void setBillPaymentErrorRecords(List<BillPaymentErrorRecords> billPaymentErrorRecords) {
        this.billPaymentErrorRecords = billPaymentErrorRecords;
    }

    public CustomerTransactionRequest getCustomerTransactionRequest() {
        return customerTransactionRequest;
    }

    public void setCustomerTransactionRequest(CustomerTransactionRequest customerTransactionRequest) {
        this.customerTransactionRequest = customerTransactionRequest;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
