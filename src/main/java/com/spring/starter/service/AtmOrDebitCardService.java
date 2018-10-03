package com.spring.starter.service;

import com.spring.starter.DTO.*;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;


public interface AtmOrDebitCardService {

    public ResponseEntity<?> atmOrDebitCardRequest(AtmOrDebitCardRequestDTO atmOrDebitCardRequestDTO, HttpServletRequest request);

    public ResponseEntity<?> reIssueAPin(ReIssuePinRequestDTO reIssuePinRequestDTO, HttpServletRequest request);

    public ResponseEntity<?> smsSubscription(SmsSubscriptionDTO smsSubscriptionDTO, HttpServletRequest request);

    public ResponseEntity<?> increasePosLimit(PosLimitDTO posLimitDTO, HttpServletRequest request);

    public ResponseEntity<?> accountLinkedToTheCard(LinkedAccountDTO linkedAccountDTO, HttpServletRequest request);

    public ResponseEntity<?> changePrimaryAccount(ChangePrimaryAccountDTO changePrimaryAccountDTO, HttpServletRequest request);

}
