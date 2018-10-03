package com.spring.starter.service;

import org.springframework.http.ResponseEntity;

import com.spring.starter.DTO.SMSAlertsForCreditCardDTO;

public interface SmsAlertForCreditCardService {

    public ResponseEntity<?> smsAlertForCreditCardRequest(SMSAlertsForCreditCardDTO smsAlertsForCreditCardDTO, int customerServiceRequestId);
}
