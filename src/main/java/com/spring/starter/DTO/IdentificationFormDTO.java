package com.spring.starter.DTO;

import org.springframework.web.multipart.MultipartFile;
import java.io.Serializable;

public class IdentificationFormDTO implements Serializable {

    private String identification;
    private MultipartFile file;
    private int customerServiceRequestId;

    public IdentificationFormDTO() {
    }

    public IdentificationFormDTO(String identification, MultipartFile file, int customerServiceRequestId) {
        this.identification = identification;
        this.file = file;
        this.customerServiceRequestId = customerServiceRequestId;
    }

    public String getIdentification() {
        return identification;
    }

    public void setIdentification(String identification) {
        this.identification = identification;
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

    public int getCustomerServiceRequestId() {
        return customerServiceRequestId;
    }

    public void setCustomerServiceRequestId(int customerServiceRequestId) {
        this.customerServiceRequestId = customerServiceRequestId;
    }
}
