package com.spring.starter.DTO;

import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

public class SignatureDTO implements Serializable {

    private int service_request_id;
    private MultipartFile file;

    public SignatureDTO() {
    }

    public SignatureDTO(int service_request_id, MultipartFile file) {
        this.service_request_id = service_request_id;
        this.file = file;
    }

    public int getService_request_id() {
        return service_request_id;
    }

    public void setService_request_id(int service_request_id) {
        this.service_request_id = service_request_id;
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }
}
