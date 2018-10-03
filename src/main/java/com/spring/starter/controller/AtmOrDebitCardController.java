package com.spring.starter.controller;

import com.spring.starter.DTO.*;
import com.spring.starter.model.ResponseModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.starter.service.AtmOrDebitCardService;

import java.util.Calendar;
import java.util.Date;

@RestController
@RequestMapping("/serviceRequest/atmOrDebit")
public class AtmOrDebitCardController {

    @Autowired
    private AtmOrDebitCardService atmOrDebitCardService;

    @PostMapping("/request")
    public ResponseEntity<?> addAtmOrDebitRequest(@RequestBody AtmOrDebitCardRequestDTO atmOrDebitCardRequestDTO) {
        return atmOrDebitCardService.atmOrDebitCardRequest(atmOrDebitCardRequestDTO);
    }

    @PutMapping("/request")
    public ResponseEntity<?> addAtmOrDebitRequestUpdate(@RequestBody AtmOrDebitCardRequestDTO atmOrDebitCardRequestDTO) {
        return atmOrDebitCardService.atmOrDebitCardRequest(atmOrDebitCardRequestDTO);
    }

    @PostMapping("/reIssuePin")
    public ResponseEntity<?> reIssueAPin(@RequestBody ReIssuePinRequestDTO reIssuePinRequestDTO) {
        return atmOrDebitCardService.reIssueAPin(reIssuePinRequestDTO);
    }

    @PutMapping("/reIssuePin")
    public ResponseEntity<?> reIssueAPinUpdate(@RequestBody ReIssuePinRequestDTO reIssuePinRequestDTO) {
        return atmOrDebitCardService.reIssueAPin(reIssuePinRequestDTO);
    }

    @PostMapping("/smsSubscription")
    public ResponseEntity<?> smsSubscription(@RequestBody SmsSubscriptionDTO smsSubscriptionDTO) {
        return atmOrDebitCardService.smsSubscription(smsSubscriptionDTO);
    }

    @PutMapping("/smsSubscription")
    public ResponseEntity<?> smsSubscriptionUpdate(@RequestBody SmsSubscriptionDTO smsSubscriptionDTO) {
        return atmOrDebitCardService.smsSubscription(smsSubscriptionDTO);
    }

    @PostMapping("/posLimit")
    public ResponseEntity<?> IncreasePosLimit(@RequestBody PosLimitDTO posLimitDTO) {
        return atmOrDebitCardService.increasePosLimit(posLimitDTO);
    }

    @PutMapping("/posLimit")
    public ResponseEntity<?> IncreasePosLimitUpdate(@RequestBody PosLimitDTO posLimitDTO) {
        return atmOrDebitCardService.increasePosLimit(posLimitDTO);
    }

    @PostMapping("/LinkedAccount")
    public ResponseEntity<?> LinkedAccount(@RequestBody LinkedAccountDTO linkedAccountDTO) {
        return atmOrDebitCardService.accountLinkedToTheCard(linkedAccountDTO);
    }

    @PutMapping("/LinkedAccount")
    public ResponseEntity<?> LinkedAccountUpdate(@RequestBody LinkedAccountDTO linkedAccountDTO) {
        return atmOrDebitCardService.accountLinkedToTheCard(linkedAccountDTO);
    }

    @PostMapping("/changePrimaryAccount")
    public ResponseEntity<?> ChangePrimaryAccount(@RequestBody ChangePrimaryAccountDTO changePrimaryAccountDTO) {
        return atmOrDebitCardService.changePrimaryAccount(changePrimaryAccountDTO);
    }

    @PutMapping("/changePrimaryAccount")
    public ResponseEntity<?> ChangePrimaryAccountUpdate(@RequestBody ChangePrimaryAccountDTO changePrimaryAccountDTO) {
        return atmOrDebitCardService.changePrimaryAccount(changePrimaryAccountDTO);
    }

}
