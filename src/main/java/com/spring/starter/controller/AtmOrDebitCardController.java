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

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Calendar;
import java.util.Date;

@RestController
@RequestMapping("/serviceRequest/atmOrDebit")
public class AtmOrDebitCardController {

    @Autowired
    private AtmOrDebitCardService atmOrDebitCardService;

    @PostMapping("/request")
    public ResponseEntity<?> addAtmOrDebitRequest(@RequestBody @Valid AtmOrDebitCardRequestDTO atmOrDebitCardRequestDTO, HttpServletRequest request) {
        return atmOrDebitCardService.atmOrDebitCardRequest(atmOrDebitCardRequestDTO, request);
    }

    @PutMapping("/request")
    public ResponseEntity<?> addAtmOrDebitRequestUpdate(@RequestBody @Valid AtmOrDebitCardRequestDTO atmOrDebitCardRequestDTO, HttpServletRequest request) {
        return atmOrDebitCardService.atmOrDebitCardRequest(atmOrDebitCardRequestDTO, request);
    }

    @PostMapping("/reIssuePin")
    public ResponseEntity<?> reIssueAPin(@RequestBody ReIssuePinRequestDTO reIssuePinRequestDTO, HttpServletRequest request) {
        return atmOrDebitCardService.reIssueAPin(reIssuePinRequestDTO, request);
    }

    @PutMapping("/reIssuePin")
    public ResponseEntity<?> reIssueAPinUpdate(@RequestBody ReIssuePinRequestDTO reIssuePinRequestDTO, HttpServletRequest request) {
        return atmOrDebitCardService.reIssueAPin(reIssuePinRequestDTO, request);
    }

    @PostMapping("/smsSubscription")
    public ResponseEntity<?> smsSubscription(@RequestBody SmsSubscriptionDTO smsSubscriptionDTO, HttpServletRequest request) {
        return atmOrDebitCardService.smsSubscription(smsSubscriptionDTO, request);
    }

    @PutMapping("/smsSubscription")
    public ResponseEntity<?> smsSubscriptionUpdate(@RequestBody SmsSubscriptionDTO smsSubscriptionDTO, HttpServletRequest request) {
        return atmOrDebitCardService.smsSubscription(smsSubscriptionDTO, request);
    }

    @PostMapping("/posLimit")
    public ResponseEntity<?> IncreasePosLimit(@RequestBody PosLimitDTO posLimitDTO, HttpServletRequest request) {
        return atmOrDebitCardService.increasePosLimit(posLimitDTO, request);
    }

    @PutMapping("/posLimit")
    public ResponseEntity<?> IncreasePosLimitUpdate(@RequestBody PosLimitDTO posLimitDTO, HttpServletRequest request) {
        return atmOrDebitCardService.increasePosLimit(posLimitDTO, request);
    }

    @PostMapping("/LinkedAccount")
    public ResponseEntity<?> LinkedAccount(@RequestBody LinkedAccountDTO linkedAccountDTO, HttpServletRequest request) {
        return atmOrDebitCardService.accountLinkedToTheCard(linkedAccountDTO, request);
    }

    @PutMapping("/LinkedAccount")
    public ResponseEntity<?> LinkedAccountUpdate(@RequestBody LinkedAccountDTO linkedAccountDTO, HttpServletRequest request) {
        return atmOrDebitCardService.accountLinkedToTheCard(linkedAccountDTO, request);
    }

    @PostMapping("/changePrimaryAccount")
    public ResponseEntity<?> ChangePrimaryAccount(@RequestBody ChangePrimaryAccountDTO changePrimaryAccountDTO, HttpServletRequest request) {
        return atmOrDebitCardService.changePrimaryAccount(changePrimaryAccountDTO, request);
    }

    @PutMapping("/changePrimaryAccount")
    public ResponseEntity<?> ChangePrimaryAccountUpdate(@RequestBody ChangePrimaryAccountDTO changePrimaryAccountDTO, HttpServletRequest request) {
        return atmOrDebitCardService.changePrimaryAccount(changePrimaryAccountDTO, request);
    }

}
