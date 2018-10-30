package com.spring.starter.DTO;

import com.spring.starter.model.CSRQueue;

public class CSRQueueDTO {

    private String message;

    private CSRQueue csrQueue;

    public CSRQueueDTO() {
    }

    public CSRQueueDTO(String message, CSRQueue csrQueue) {
        this.message = message;
        this.csrQueue = csrQueue;
    }


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public CSRQueue getCsrQueue() {
        return csrQueue;
    }

    public void setCsrQueue(CSRQueue csrQueue) {
        this.csrQueue = csrQueue;
    }
}
