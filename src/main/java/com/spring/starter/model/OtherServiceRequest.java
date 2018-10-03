package com.spring.starter.model;

import javax.persistence.*;
import java.util.Date;

@Entity
public class OtherServiceRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int otherid;
    private String requestMsg;
    private Date date;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="csrId")
    private CustomerServiceRequest customerServiceRequest;

    public OtherServiceRequest() {
    }

    public int getOtherid() {
        return otherid;
    }

    public void setOtherid(int otherid) {
        this.otherid = otherid;
    }

    public String getRequestMsg() {
        return requestMsg;
    }

    public void setRequestMsg(String requestMsg) {
        this.requestMsg = requestMsg;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public CustomerServiceRequest getCustomerServiceRequest() {
        return customerServiceRequest;
    }

    public void setCustomerServiceRequest(CustomerServiceRequest customerServiceRequest) {
        this.customerServiceRequest = customerServiceRequest;
    }
}
