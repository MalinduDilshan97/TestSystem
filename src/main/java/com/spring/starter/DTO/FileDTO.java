package com.spring.starter.DTO;

import org.springframework.web.multipart.MultipartFile;

public class FileDTO {

    private String fileType;

    private MultipartFile file;

    private int customerTransactionRequestId;

    public FileDTO() {
    }

    public FileDTO(String fileType, MultipartFile file, int customerTransactionRequestId) {
        this.fileType = fileType;
        this.file = file;
        this.customerTransactionRequestId = customerTransactionRequestId;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

    public int getCustomerTransactionRequestId() {
        return customerTransactionRequestId;
    }

    public void setCustomerTransactionRequestId(int customerTransactionRequestId) {
        this.customerTransactionRequestId = customerTransactionRequestId;
    }
}
