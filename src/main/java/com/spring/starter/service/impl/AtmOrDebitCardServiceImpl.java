package com.spring.starter.service.impl;

import javax.transaction.Transactional;

import com.spring.starter.DTO.*;
import com.spring.starter.Repository.*;
import com.spring.starter.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.spring.starter.service.AtmOrDebitCardService;

import java.util.Optional;

@Service
@Transactional
public class AtmOrDebitCardServiceImpl implements AtmOrDebitCardService {

    @Autowired
    private CustomerServiceRequestRepository customerServiceRequestRepository;
    @Autowired
    private AtmOrDebitCardRequestRepository atmOrDebitCardRequestRepository;
    @Autowired
    private ReIssuePinRequestRepository reIssuePinRequestRepository;
    @Autowired
    private SmsSubscriptionRepository smsSubscriptionRepository;
    @Autowired
    private PosLimitRepository posLimitRepository;
    @Autowired
    private LinkedAccountRepository linkedAccountRepository;
    @Autowired
    private ChangePrimaryAccountRepository changePrimaryAccountRepository;

    private ResponseModel res = new ResponseModel();

    @Override
    public ResponseEntity<?> atmOrDebitCardRequest(AtmOrDebitCardRequestDTO atmOrDebitCardRequestDTO) {

        Optional<CustomerServiceRequest> optional=customerServiceRequestRepository.findById(atmOrDebitCardRequestDTO.getCustomerServiceRequestId());
        if (!optional.isPresent()){
            res.setMessage(" No Data Found To Complete The Request");
            res.setStatus(false);
            return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
        }else{

            CustomerServiceRequest customerServiceRequest=optional.get();

            AtmOrDebitCardRequest atmOrDebitCardRequest=new AtmOrDebitCardRequest(0,
                    atmOrDebitCardRequestDTO.getCardNumber(),customerServiceRequest);

            if (atmOrDebitCardRequestRepository.save(atmOrDebitCardRequest)!=null){
                res.setMessage(" Request Successfully Saved To The System");
                res.setStatus(true);
                return new ResponseEntity<>(res, HttpStatus.CREATED);
            }else{
                res.setMessage(" Failed TO Save The Request... Operation Unsuccessful");
                res.setStatus(false);
                return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
            }

        }

    }

    @Override
    public ResponseEntity<?> reIssueAPin(ReIssuePinRequestDTO reIssuePinRequestDTO) {
        Optional<CustomerServiceRequest> optional=customerServiceRequestRepository.findById(reIssuePinRequestDTO.getCustomerServiceRequestId());
        if (!optional.isPresent()){
            res.setMessage(" No Data Found To Complete The Request");
            res.setStatus(false);
            return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
        }else{

            CustomerServiceRequest customerServiceRequest=optional.get();

            ReIssuePinRequest reIssuePinRequest= new ReIssuePinRequest(0,
                    reIssuePinRequestDTO.getBranch(),
                    reIssuePinRequestDTO.getAddress(),
                    customerServiceRequest);

            if (reIssuePinRequestRepository.save(reIssuePinRequest)!=null){
                res.setMessage(" Request Successfully Saved To The System");
                res.setStatus(true);
                return new ResponseEntity<>(res, HttpStatus.CREATED);
            }else{
                res.setMessage(" Failed TO Save The Request... Operation Unsuccessful");
                res.setStatus(false);
                return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
            }

        }
    }

    @Override
    public ResponseEntity<?> smsSubscription(SmsSubscriptionDTO smsSubscriptionDTO) {
        Optional<CustomerServiceRequest> optional = customerServiceRequestRepository.findById(smsSubscriptionDTO.getCustomerServiceRequestId());
        if (!optional.isPresent()) {
            res.setMessage(" No Data Found To Complete The Request");
            res.setStatus(false);
            return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
        } else {

            CustomerServiceRequest customerServiceRequest = optional.get();

            SmsSubscription smsSubscription = new SmsSubscription(0, smsSubscriptionDTO.getCardNumber(),
                    smsSubscriptionDTO.getMobileNumber(),
                    customerServiceRequest);

            SmsSubscription save = smsSubscriptionRepository.save(smsSubscription);

            if (save != null) {
                res.setMessage(" Request Successfully Saved To The System");
                res.setStatus(true);
                return new ResponseEntity<>(res, HttpStatus.CREATED);
            } else {
                res.setMessage(" Failed TO Save The Request... Operation Unsuccessful");
                res.setStatus(false);
                return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
            }

        }
    }

    @Override
    public ResponseEntity<?> increasePosLimit(PosLimitDTO posLimitDTO) {
        Optional<CustomerServiceRequest> optional = customerServiceRequestRepository.findById(posLimitDTO.getCustomerServiceRequestId());
            if (!optional.isPresent()) {
                res.setMessage(" No Data Found To Complete The Request");
                res.setStatus(false);
                return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
            } else {

                CustomerServiceRequest customerServiceRequest = optional.get();

            PosLimit posLimit= new PosLimit(0,posLimitDTO.getCardNumber(),posLimitDTO.getValue(),customerServiceRequest);

            if (posLimitRepository.save(posLimit)!=null){
                res.setMessage(" Request Successfully Saved To The System");
                res.setStatus(true);
                return new ResponseEntity<>(res, HttpStatus.CREATED);
            }else{
                res.setMessage(" Failed TO Save The Request... Operation Unsuccessful");
                res.setStatus(false);
                return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
            }

        }
    }

    @Override
    public ResponseEntity<?> accountLinkedToTheCard(LinkedAccountDTO linkedAccountDTO) {
        Optional<CustomerServiceRequest> optional = customerServiceRequestRepository.findById(linkedAccountDTO.getCustomerServiceRequestId());
        if (!optional.isPresent()) {
            res.setMessage(" No Data Found To Complete The Request");
            res.setStatus(false);
            return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
        } else {

            CustomerServiceRequest customerServiceRequest = optional.get();

            LinkedAccount linkedAccount= new LinkedAccount(0,
                    linkedAccountDTO.getCardNumber(),
                    linkedAccountDTO.getPrimaryAccount(),
                    linkedAccountDTO.getSecondaryAccount(),
                    customerServiceRequest);

            if (linkedAccountRepository.save(linkedAccount)!=null){
                res.setMessage(" Request Successfully Saved To The System");
                res.setStatus(true);
                return new ResponseEntity<>(res, HttpStatus.CREATED);
            }else{
                res.setMessage(" Failed TO Save The Request... Operation Unsuccessful");
                res.setStatus(false);
                return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
            }
        }
    }

    @Override
    public ResponseEntity<?> changePrimaryAccount(ChangePrimaryAccountDTO changePrimaryAccountDTO) {
        Optional<CustomerServiceRequest> optional = customerServiceRequestRepository.findById(changePrimaryAccountDTO.getCustomerServiceRequestId());
        if (!optional.isPresent()) {
            res.setMessage(" No Data Found To Complete The Request");
            res.setStatus(false);
            return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
        } else {

            CustomerServiceRequest customerServiceRequest = optional.get();

            ChangePrimaryAccount changePrimaryAccount= new ChangePrimaryAccount(0,changePrimaryAccountDTO.getCardNumber(),
                    changePrimaryAccountDTO.getAccountNumber(),customerServiceRequest);

            if (changePrimaryAccountRepository.save(changePrimaryAccount)!=null){
                res.setMessage(" Request Successfully Saved To The System");
                res.setStatus(true);
                return new ResponseEntity<>(res, HttpStatus.CREATED);
            }else{
                res.setMessage(" Failed TO Save The Request... Operation Unsuccessful");
                res.setStatus(false);
                return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
            }
        }
    }
}
