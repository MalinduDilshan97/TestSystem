package com.spring.starter.DTO;

public class DocumentDTO {

    private int digiformId;
    private String document;

    public DocumentDTO() {
    }

    public DocumentDTO(int digiformId, String document) {
        this.digiformId = digiformId;
        this.document = document;
    }

    public int getDigiformId() {
        return digiformId;
    }

    public void setDigiformId(int digiformId) {
        this.digiformId = digiformId;
    }

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }
}
