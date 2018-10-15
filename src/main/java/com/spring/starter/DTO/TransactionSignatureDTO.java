package com.spring.starter.DTO;

import org.springframework.web.multipart.MultipartFile;

public class TransactionSignatureDTO {

    private String message;
    private int customerTransactionId;
    private MultipartFile file;

    public TransactionSignatureDTO() {
    }

    public TransactionSignatureDTO(String message, int customerTransactionId, MultipartFile file) {
        this.message = message;
        this.customerTransactionId = customerTransactionId;
        this.file = file;
    }

    public int getCustomerTransactionId() {
        return customerTransactionId;
    }

    public void setCustomerTransactionId(int customerTransactionId) {
        this.customerTransactionId = customerTransactionId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }


}
