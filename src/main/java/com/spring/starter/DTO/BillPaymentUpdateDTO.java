package com.spring.starter.DTO;

import org.springframework.web.multipart.MultipartFile;

public class BillPaymentUpdateDTO {

    private String comment;
    private MultipartFile file;

    public BillPaymentUpdateDTO() {
    }

    public BillPaymentUpdateDTO(String comment, MultipartFile file) {
        this.comment = comment;
        this.file = file;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }
}
