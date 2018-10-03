package com.spring.starter.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class CustomException extends RuntimeException {

    private static final long serialVersionUID = -4762953089468482194L;

    public CustomException() {

    }

    public CustomException(String message) {
        super(message);
    }
}