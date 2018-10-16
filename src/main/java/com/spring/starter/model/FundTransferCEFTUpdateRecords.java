package com.spring.starter.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.List;

@Entity
@Table(name = "fund_transfer_CEFTE_update_records")
public class FundTransferCEFTUpdateRecords {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int fundTrasnferCEFTUpdateRecordsId;

    @NotNull
    @Pattern(regexp = "^([A-Za-z0-9_\\s])*$")
    private String comment;

    private String url;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "fund_trasnfer_CEFT_update_record_Id")
    private List<FundTransferWithinNDBErrorRecords> fundTransferWithinNDBErrorRecords;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_service_request_id")
    private CustomerTransactionRequest customerTransactionRequest;

    public FundTransferCEFTUpdateRecords() {
    }

    public FundTransferCEFTUpdateRecords(@NotNull @Pattern(regexp = "^([A-Za-z0-9_\\s])*$") String comment, String url,
                                         List<FundTransferWithinNDBErrorRecords> fundTransferWithinNDBErrorRecords,
                                         CustomerTransactionRequest customerTransactionRequest) {
        this.comment = comment;
        this.url = url;
        this.fundTransferWithinNDBErrorRecords = fundTransferWithinNDBErrorRecords;
        this.customerTransactionRequest = customerTransactionRequest;
    }

    public int getFundTrasnferCEFTUpdateRecordsId() {
        return fundTrasnferCEFTUpdateRecordsId;
    }

    public void setFundTrasnferCEFTUpdateRecordsId(int fundTrasnferCEFTUpdateRecordsId) {
        this.fundTrasnferCEFTUpdateRecordsId = fundTrasnferCEFTUpdateRecordsId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<FundTransferWithinNDBErrorRecords> getFundTransferWithinNDBErrorRecords() {
        return fundTransferWithinNDBErrorRecords;
    }

    public void setFundTransferWithinNDBErrorRecords(List<FundTransferWithinNDBErrorRecords> fundTransferWithinNDBErrorRecords) {
        this.fundTransferWithinNDBErrorRecords = fundTransferWithinNDBErrorRecords;
    }

    public CustomerTransactionRequest getCustomerTransactionRequest() {
        return customerTransactionRequest;
    }

    public void setCustomerTransactionRequest(CustomerTransactionRequest customerTransactionRequest) {
        this.customerTransactionRequest = customerTransactionRequest;
    }
}
