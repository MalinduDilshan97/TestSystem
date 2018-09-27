package com.spring.starter.service;

import com.spring.starter.DTO.*;
import org.springframework.http.ResponseEntity;


public interface AtmOrDebitCardService {

    public ResponseEntity<?> atmOrDebitCardRequest(AtmOrDebitCardRequestDTO atmOrDebitCardRequestDTO);

    public ResponseEntity<?> reIssueAPin(ReIssuePinRequestDTO reIssuePinRequestDTO);

    public ResponseEntity<?> smsSubscription(SmsSubscriptionDTO smsSubscriptionDTO);

    public ResponseEntity<?> increasePosLimit(PosLimitDTO posLimitDTO);

    public  ResponseEntity<?> accountLinkedToTheCard(LinkedAccountDTO linkedAccountDTO);

    public ResponseEntity<?> changePrimaryAccount(ChangePrimaryAccountDTO changePrimaryAccountDTO);

}
