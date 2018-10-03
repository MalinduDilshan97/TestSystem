package com.spring.starter.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.spring.starter.DTO.SMSAlertsForCreditCardDTO;
import com.spring.starter.service.SmsAlertForCreditCardService;

@RestController
@RequestMapping("/serviceRequest/SMSAlertsforCreditCard")
@CrossOrigin
public class SMSAlertsforCreditcardController {

    @Autowired
    SmsAlertForCreditCardService smsAlertForCreditCardService;

    @PostMapping
    public ResponseEntity<?> changePermenentMailRequest(@RequestBody @Valid SMSAlertsForCreditCardDTO smsAlertsForCreditCardDTO, @RequestParam(name = "requestId") int requestId) {
        return smsAlertForCreditCardService.smsAlertForCreditCardRequest(smsAlertsForCreditCardDTO, requestId);
    }

    @GetMapping
    public ResponseEntity<?> test() {
        SMSAlertsForCreditCardDTO test = new SMSAlertsForCreditCardDTO();
        List<String> ccNumbers = new ArrayList<String>();
        ccNumbers.add("31213213213");
        ccNumbers.add("12321324321");
        test.setCreditCardNumbers(ccNumbers);
        test.setMobileNumber("0342252011");
        return new ResponseEntity<>(test, HttpStatus.OK);
    }


}
