package com.spring.starter.service;

import org.springframework.http.ResponseEntity;

import com.spring.starter.DTO.MailingMailDTO;

public interface MailingMailService {

    public ResponseEntity<?> addchangeMailingMailRequest(MailingMailDTO mailingMailDTO, int customerServiceRequestId);

}
