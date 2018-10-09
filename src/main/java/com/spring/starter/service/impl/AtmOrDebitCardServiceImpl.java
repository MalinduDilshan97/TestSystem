package com.spring.starter.service.impl;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import com.spring.starter.DTO.*;
import com.spring.starter.Repository.*;
import com.spring.starter.configuration.ServiceRequestIdConfig;
import com.spring.starter.model.*;
import com.spring.starter.service.ServiceRequestCustomerLogService;
import com.spring.starter.service.ServiceRequestFormLogService;
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
    @Autowired
    private NDBBranchRepository ndbBranchRepository;
    @Autowired
    private ServiceRequestCustomerLogService serviceRequestCustomerLogService;
    @Autowired
    private ServiceRequestFormLogService serviceRequestFormLogService;
    @Autowired
    private Card_Validation card_validation;

    private ResponseModel res = new ResponseModel();
    private ServiceRequestCustomerLog serviceRequestCustomerLog = new ServiceRequestCustomerLog();
    private ServiceRequestFormLog serviceRequestFormLog = new ServiceRequestFormLog();

    @Override
    public ResponseEntity<?> atmOrDebitCardRequest(AtmOrDebitCardRequestDTO atmOrDebitCardRequestDTO, HttpServletRequest request) {

        Optional<CustomerServiceRequest> optional=customerServiceRequestRepository.findById(atmOrDebitCardRequestDTO.getCustomerServiceRequestId());
        if (!optional.isPresent()){
            res.setMessage("No Data Found To Complete The Request");
            res.setStatus(false);
            return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
        }
        CustomerServiceRequest customerServiceRequest=optional.get();
        AtmOrDebitCardRequest atmOrDebitCardRequest=new AtmOrDebitCardRequest();

        int serviceRequestId = customerServiceRequest.getServiceRequest().getDigiFormId();
        if(serviceRequestId != ServiceRequestIdConfig.CARD_REQUEST) {
            res.setMessage("Invalid Request");
            res.setStatus(false);
            return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
        }
        Optional<AtmOrDebitCardRequest> optionalRequest=atmOrDebitCardRequestRepository.getFormFromCSR(atmOrDebitCardRequestDTO.getCustomerServiceRequestId());
        if (optionalRequest.isPresent()){
            atmOrDebitCardRequest.setAtmOrDebitRequestId(optionalRequest.get().getAtmOrDebitRequestId());
        }

            atmOrDebitCardRequest.setRequestType(atmOrDebitCardRequestDTO.getRequestType());
            atmOrDebitCardRequest.setCardNumber(atmOrDebitCardRequestDTO.getCardNumber());
            atmOrDebitCardRequest.setCustomerServiceRequest(customerServiceRequest);

        if (atmOrDebitCardRequestRepository.save(atmOrDebitCardRequest) != null) {

            serviceRequestCustomerLog.setDate(java.util.Calendar.getInstance().getTime());
            serviceRequestCustomerLog.setIdentification(customerServiceRequest.getCustomer().getIdentification());
            serviceRequestCustomerLog.setIp(request.getRemoteAddr());
            serviceRequestCustomerLog.setMessage("Request Form Successfully Saved To The System");
            boolean result = serviceRequestCustomerLogService.saveServiceRequestCustomerLog(serviceRequestCustomerLog);

            if (result) {
                serviceRequestFormLog.setDigiFormId(customerServiceRequest.getServiceRequest().getDigiFormId());
                serviceRequestFormLog.setCustomerId(customerServiceRequest.getCustomer().getCustomerId());
                serviceRequestFormLog.setDate(java.util.Calendar.getInstance().getTime());
                serviceRequestFormLog.setFromId(customerServiceRequest.getServiceRequest().getServiceRequestId());
                serviceRequestFormLog.setIp(request.getRemoteAddr());
                serviceRequestFormLog.setStatus(true);
                serviceRequestFormLog.setMessage("Request Form Successfully Saved To The  System");
                serviceRequestFormLogService.saveServiceRequestFormLog(serviceRequestFormLog);
            }

            res.setMessage(" Request Successfully Saved To The Database");
            res.setStatus(true);
            return new ResponseEntity<>(res, HttpStatus.CREATED);
        } else {

            serviceRequestCustomerLog.setDate(java.util.Calendar.getInstance().getTime());
            serviceRequestCustomerLog.setIdentification(customerServiceRequest.getCustomer().getIdentification());
            serviceRequestCustomerLog.setIp(request.getRemoteAddr());
            serviceRequestCustomerLog.setMessage("Failed TO Save The Request... Operation Unsuccessful");
            boolean result = serviceRequestCustomerLogService.saveServiceRequestCustomerLog(serviceRequestCustomerLog);

            if (result) {
                serviceRequestFormLog.setDigiFormId(customerServiceRequest.getServiceRequest().getDigiFormId());
                serviceRequestFormLog.setCustomerId(customerServiceRequest.getCustomer().getCustomerId());
                serviceRequestFormLog.setDate(java.util.Calendar.getInstance().getTime());
                serviceRequestFormLog.setFromId(customerServiceRequest.getServiceRequest().getServiceRequestId());
                serviceRequestFormLog.setIp(request.getRemoteAddr());
                serviceRequestFormLog.setStatus(true);
                serviceRequestFormLog.setMessage(" Failed TO Save The  Request... Operation Unsuccessful");
                serviceRequestFormLogService.saveServiceRequestFormLog(serviceRequestFormLog);
            }

            res.setMessage(" Failed TO Save The Request... Operation Unsuccessful");
            res.setStatus(false);
            return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity<?> reIssueAPin(ReIssuePinRequestDTO reIssuePinRequestDTO, HttpServletRequest request) {
        Optional<CustomerServiceRequest> optional=customerServiceRequestRepository.findById(reIssuePinRequestDTO.getCustomerServiceRequestId());
        if (!optional.isPresent()){
            res.setMessage(" No Data Found To Complete The Request");
            res.setStatus(false);
            return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
        }

            ReIssuePinRequest reIssuePinRequest= new ReIssuePinRequest();
            CustomerServiceRequest customerServiceRequest=optional.get();

        int serviceRequestId = customerServiceRequest.getServiceRequest().getDigiFormId();
        if(serviceRequestId != ServiceRequestIdConfig.RE_ISSUE_A_PIN) {
            res.setMessage("Invalid Request");
            res.setStatus(false);
            return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
        }

        Optional<ReIssuePinRequest> pinRequest=reIssuePinRequestRepository.getFormFromCSR(reIssuePinRequestDTO.getCustomerServiceRequestId());
        if (pinRequest.isPresent()){
            reIssuePinRequest.setReIssuePinRequestId(pinRequest.get().getReIssuePinRequestId());
        }
        if(reIssuePinRequestDTO.isAvAddress()){
            if(reIssuePinRequestDTO.getAddress() == null){
                res.setMessage("Complete Address details");
                res.setStatus(false);
                return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
            }
        }
        if(reIssuePinRequestDTO.isAvBranch()){
            if(reIssuePinRequestDTO.getBranch() == 0){
                res.setMessage("Complete Branch Details");
                res.setStatus(false);
                return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
            }
            Optional<NDBBranch> ndbBranchOpt = ndbBranchRepository.findById(reIssuePinRequestDTO.getBranch());
            if(!ndbBranchOpt.isPresent()){
                res.setMessage("Invalied Branch Details");
                res.setStatus(false);
                return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
            }
            reIssuePinRequest.setNdbBranch(ndbBranchOpt.get());
        }

            reIssuePinRequest.setCurrespondingAddress(reIssuePinRequestDTO.isAvCurrespondingAddress());
            reIssuePinRequest.setAddress(reIssuePinRequestDTO.isAvAddress());
            reIssuePinRequest.setBranch(reIssuePinRequestDTO.isAvBranch());
            reIssuePinRequest.setAddress(reIssuePinRequestDTO.getAddress());
            reIssuePinRequest.setCustomerServiceRequest(customerServiceRequest);

            if (reIssuePinRequestRepository.save(reIssuePinRequest)!=null){
                res.setMessage("Request Successfully Saved To The System");
                res.setStatus(true);
                return new ResponseEntity<>(res, HttpStatus.CREATED);
            }else{
                res.setMessage(" Failed TO Save The Request... Operation Unsuccessful");
                res.setStatus(false);
                return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
            }
    }

    @Override
    public ResponseEntity<?> smsSubscription(SmsSubscriptionDTO smsSubscriptionDTO, HttpServletRequest request) {
        Optional<CustomerServiceRequest> optional = customerServiceRequestRepository.findById(smsSubscriptionDTO.getCustomerServiceRequestId());
        if (!optional.isPresent()) {
            res.setMessage(" No Data Found To Complete The Request");
            res.setStatus(false);
            return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
        } else {

            CustomerServiceRequest customerServiceRequest = optional.get();
            SmsSubscription smsSubscription = new SmsSubscription();

            int serviceRequestId = customerServiceRequest.getServiceRequest().getDigiFormId();
            if(serviceRequestId != ServiceRequestIdConfig.SUBSCRIBE_TO_SMS_ALERTS_FOR_CARD_TRANSACTIONS) {
                res.setMessage("Invalid Request");
                res.setStatus(false);
                return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
            }

            Optional<SmsSubscription> subscription=smsSubscriptionRepository.getFormFromCSR(serviceRequestId);
            if (subscription.isPresent()){
                smsSubscription.setSubscriptionId(subscription.get().getSubscriptionId());
            }

            if(!card_validation.checkCardValidity(Long.toString(smsSubscriptionDTO.getCardNumber()))){
                res.setMessage("Invalid Card Details");
                res.setStatus(false);
                return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
            }

            smsSubscription.setSubscriptionId(smsSubscriptionDTO.getSubscriptionId());
            smsSubscription.setCardNumber(smsSubscriptionDTO.getCardNumber());
            smsSubscription.setMobileNumber(smsSubscriptionDTO.getMobileNumber());
            smsSubscription.setCustomerServiceRequest(customerServiceRequest);

            SmsSubscription save = smsSubscriptionRepository.save(smsSubscription);

            if (save != null) {

                serviceRequestCustomerLog.setDate(java.util.Calendar.getInstance().getTime());
                serviceRequestCustomerLog.setIdentification(customerServiceRequest.getCustomer().getIdentification());
                serviceRequestCustomerLog.setIp(request.getRemoteAddr());
                serviceRequestCustomerLog.setMessage("Request Form Successfully Saved To The System");
                boolean result = serviceRequestCustomerLogService.saveServiceRequestCustomerLog(serviceRequestCustomerLog);

                if (result) {
                    serviceRequestFormLog.setDigiFormId(customerServiceRequest.getServiceRequest().getDigiFormId());
                    serviceRequestFormLog.setCustomerId(customerServiceRequest.getCustomer().getCustomerId());
                    serviceRequestFormLog.setDate(java.util.Calendar.getInstance().getTime());
                    serviceRequestFormLog.setFromId(customerServiceRequest.getServiceRequest().getServiceRequestId());
                    serviceRequestFormLog.setIp(request.getRemoteAddr());
                    serviceRequestFormLog.setStatus(true);
                    serviceRequestFormLog.setMessage(" Request Form Successfully Saved To The System");
                    serviceRequestFormLogService.saveServiceRequestFormLog(serviceRequestFormLog);
                }

                res.setMessage(" Request Successfully Saved To The System");
                res.setStatus(true);
                return new ResponseEntity<>(res, HttpStatus.CREATED);
            } else {

                serviceRequestCustomerLog.setDate(java.util.Calendar.getInstance().getTime());
                serviceRequestCustomerLog.setIdentification(customerServiceRequest.getCustomer().getIdentification());
                serviceRequestCustomerLog.setIp(request.getRemoteAddr());
                serviceRequestCustomerLog.setMessage("Failed TO Save The Request... Operation Unsuccessful");
                boolean result = serviceRequestCustomerLogService.saveServiceRequestCustomerLog(serviceRequestCustomerLog);

                if (result) {
                    serviceRequestFormLog.setDigiFormId(customerServiceRequest.getServiceRequest().getDigiFormId());
                    serviceRequestFormLog.setCustomerId(customerServiceRequest.getCustomer().getCustomerId());
                    serviceRequestFormLog.setDate(java.util.Calendar.getInstance().getTime());
                    serviceRequestFormLog.setFromId(customerServiceRequest.getServiceRequest().getServiceRequestId());
                    serviceRequestFormLog.setIp(request.getRemoteAddr());
                    serviceRequestFormLog.setStatus(true);
                    serviceRequestFormLog.setMessage(" Failed TO Save The Request...Operation Unsuccessful");
                    serviceRequestFormLogService.saveServiceRequestFormLog(serviceRequestFormLog);
                }

                res.setMessage(" Failed TO Save The Request... Operation Unsuccessful");
                res.setStatus(false);
                return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
            }

        }
    }

    @Override
    public ResponseEntity<?> increasePosLimit(PosLimitDTO posLimitDTO, HttpServletRequest request) {
        Optional<CustomerServiceRequest> optional = customerServiceRequestRepository.findById(posLimitDTO.getCustomerServiceRequestId());
            if (!optional.isPresent()) {
                res.setMessage(" No Data Found To Complete The Request");
                res.setStatus(false);
                return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
            } else {

                CustomerServiceRequest customerServiceRequest = optional.get();
                PosLimit posLimit= new PosLimit();

                int serviceRequestId = customerServiceRequest.getServiceRequest().getDigiFormId();
                if(serviceRequestId != ServiceRequestIdConfig.INCREASE_POS_LIMIT_OF_DEBIT_CARD) {
                    res.setMessage("Invalid Request");
                    res.setStatus(false);
                    return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
                }

                Optional<PosLimit> pos=posLimitRepository.getFormFromCSR(serviceRequestId);
                if (pos.isPresent()){
                    posLimit.setPosLimitId(pos.get().getPosLimitId());
                }

                if(!card_validation.checkCardValidity(Long.toString(posLimitDTO.getCardNumber()))){
                    res.setMessage("Invalid Card Details");
                    res.setStatus(false);
                    return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
                }

                posLimit.setPosLimitId(posLimitDTO.getPosLimitId());
                posLimit.setCardNumber(posLimitDTO.getCardNumber());
                posLimit.setValue(posLimitDTO.getValue());
                posLimit.setCustomerServiceRequest(customerServiceRequest);

            if (posLimitRepository.save(posLimit) != null) {

                serviceRequestCustomerLog.setDate(java.util.Calendar.getInstance().getTime());
                serviceRequestCustomerLog.setIdentification(customerServiceRequest.getCustomer().getIdentification());
                serviceRequestCustomerLog.setIp(request.getRemoteAddr());
                serviceRequestCustomerLog.setMessage("Request Form Successfully Saved To The System");
                boolean result = serviceRequestCustomerLogService.saveServiceRequestCustomerLog(serviceRequestCustomerLog);

                if (result) {
                    serviceRequestFormLog.setDigiFormId(customerServiceRequest.getServiceRequest().getDigiFormId());
                    serviceRequestFormLog.setCustomerId(customerServiceRequest.getCustomer().getCustomerId());
                    serviceRequestFormLog.setDate(java.util.Calendar.getInstance().getTime());
                    serviceRequestFormLog.setFromId(customerServiceRequest.getServiceRequest().getServiceRequestId());
                    serviceRequestFormLog.setIp(request.getRemoteAddr());
                    serviceRequestFormLog.setStatus(true);
                    serviceRequestFormLog.setMessage("  Request Form Successfully Saved To The System");
                    serviceRequestFormLogService.saveServiceRequestFormLog(serviceRequestFormLog);
                }

                res.setMessage("Request Successfully Saved To The System");
                res.setStatus(true);
                return new ResponseEntity<>(res, HttpStatus.CREATED);
            } else {

                serviceRequestCustomerLog.setDate(java.util.Calendar.getInstance().getTime());
                serviceRequestCustomerLog.setIdentification(customerServiceRequest.getCustomer().getIdentification());
                serviceRequestCustomerLog.setIp(request.getRemoteAddr());
                serviceRequestCustomerLog.setMessage("Failed TO Save The Request...  Operation Unsuccessful");
                boolean result = serviceRequestCustomerLogService.saveServiceRequestCustomerLog(serviceRequestCustomerLog);

                if (result) {
                    serviceRequestFormLog.setDigiFormId(customerServiceRequest.getServiceRequest().getDigiFormId());
                    serviceRequestFormLog.setCustomerId(customerServiceRequest.getCustomer().getCustomerId());
                    serviceRequestFormLog.setDate(java.util.Calendar.getInstance().getTime());
                    serviceRequestFormLog.setFromId(customerServiceRequest.getServiceRequest().getServiceRequestId());
                    serviceRequestFormLog.setIp(request.getRemoteAddr());
                    serviceRequestFormLog.setStatus(true);
                    serviceRequestFormLog.setMessage(" Failed TO Save The Request...  Operation  Unsuccessful");
                    serviceRequestFormLogService.saveServiceRequestFormLog(serviceRequestFormLog);
                }

                res.setMessage("Failed TO Save The Request... Operation Unsuccessful");
                res.setStatus(false);
                return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
            }

        }
    }

    @Override
    public ResponseEntity<?> accountLinkedToTheCard(LinkedAccountDTO linkedAccountDTO, HttpServletRequest request) {
        Optional<CustomerServiceRequest> optional = customerServiceRequestRepository.findById(linkedAccountDTO.getCustomerServiceRequestId());
        if (!optional.isPresent()) {
            res.setMessage(" No Data Found To Complete The Request");
            res.setStatus(false);
            return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
        } else {

            CustomerServiceRequest customerServiceRequest = optional.get();
            LinkedAccount linkedAccount= new LinkedAccount();

            int serviceRequestId = customerServiceRequest.getServiceRequest().getDigiFormId();
            if(serviceRequestId != ServiceRequestIdConfig.LINK_NEW_ACCAUNTS_TO_D13EBIT_ATM_CARD) {
                res.setMessage("Invalid Request");
                res.setStatus(false);
                return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
            }

            Optional<LinkedAccount> account=linkedAccountRepository.getFormFromCSR(serviceRequestId);
            if (account.isPresent()){
                linkedAccount.setLinkedAccountId(account.get().getLinkedAccountId());
            }
            if(!card_validation.checkCardValidity(Long.toString(linkedAccountDTO.getCardNumber()))){
                res.setMessage("Invalid Card Details");
                res.setStatus(false);
                return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
            }

            linkedAccount.setCardNumber(linkedAccountDTO.getCardNumber());
            linkedAccount.setPrimaryAccount(linkedAccountDTO.getPrimaryAccount());
            linkedAccount.setSecondaryAccount(linkedAccountDTO.getSecondaryAccount());
            linkedAccount.setCustomerServiceRequest(customerServiceRequest);

            if (linkedAccountRepository.save(linkedAccount) != null) {

                serviceRequestCustomerLog.setDate(java.util.Calendar.getInstance().getTime());
                serviceRequestCustomerLog.setIdentification(customerServiceRequest.getCustomer().getIdentification());
                serviceRequestCustomerLog.setIp(request.getRemoteAddr());
                serviceRequestCustomerLog.setMessage("Request Form Successfully Saved To The System");
                boolean result = serviceRequestCustomerLogService.saveServiceRequestCustomerLog(serviceRequestCustomerLog);

                if (result) {
                    serviceRequestFormLog.setDigiFormId(customerServiceRequest.getServiceRequest().getDigiFormId());
                    serviceRequestFormLog.setCustomerId(customerServiceRequest.getCustomer().getCustomerId());
                    serviceRequestFormLog.setDate(java.util.Calendar.getInstance().getTime());
                    serviceRequestFormLog.setFromId(customerServiceRequest.getServiceRequest().getServiceRequestId());
                    serviceRequestFormLog.setIp(request.getRemoteAddr());
                    serviceRequestFormLog.setStatus(true);
                    serviceRequestFormLog.setMessage(" Request Form Successfully Saved To The System ");
                    serviceRequestFormLogService.saveServiceRequestFormLog(serviceRequestFormLog);
                }

                res.setMessage(" Request Successfully Saved To The System.");
                res.setStatus(true);
                return new ResponseEntity<>(res, HttpStatus.CREATED);
            } else {

                serviceRequestCustomerLog.setDate(java.util.Calendar.getInstance().getTime());
                serviceRequestCustomerLog.setIdentification(customerServiceRequest.getCustomer().getIdentification());
                serviceRequestCustomerLog.setIp(request.getRemoteAddr());
                serviceRequestCustomerLog.setMessage(" Failed TO Save The Request...  Operation Unsuccessful");
                boolean result = serviceRequestCustomerLogService.saveServiceRequestCustomerLog(serviceRequestCustomerLog);

                if (result) {
                    serviceRequestFormLog.setDigiFormId(customerServiceRequest.getServiceRequest().getDigiFormId());
                    serviceRequestFormLog.setCustomerId(customerServiceRequest.getCustomer().getCustomerId());
                    serviceRequestFormLog.setDate(java.util.Calendar.getInstance().getTime());
                    serviceRequestFormLog.setFromId(customerServiceRequest.getServiceRequest().getServiceRequestId());
                    serviceRequestFormLog.setIp(request.getRemoteAddr());
                    serviceRequestFormLog.setStatus(true);
                    serviceRequestFormLog.setMessage(" Failed TO Save The Request... Operation  Unsuccessful");
                    serviceRequestFormLogService.saveServiceRequestFormLog(serviceRequestFormLog);
                }

                res.setMessage(" Failed TO Save The Request... Operation Unsuccessful");
                res.setStatus(false);
                return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
            }
        }
    }

    @Override
    public ResponseEntity<?> changePrimaryAccount(ChangePrimaryAccountDTO changePrimaryAccountDTO, HttpServletRequest request) {
        Optional<CustomerServiceRequest> optional = customerServiceRequestRepository.findById(changePrimaryAccountDTO.getCustomerServiceRequestId());
        if (!optional.isPresent()) {
            res.setMessage(" No Data Found To Complete The Request");
            res.setStatus(false);
            return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
        } else {
            CustomerServiceRequest customerServiceRequest = optional.get();
            ChangePrimaryAccount changePrimaryAccount= new ChangePrimaryAccount();

            int serviceRequestId = customerServiceRequest.getServiceRequest().getDigiFormId();
            if(serviceRequestId != ServiceRequestIdConfig.CHANGE_PRIMARY_ACCOUNT) {
                res.setMessage("Invalid Request");
                res.setStatus(false);
                return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
            }

            Optional<ChangePrimaryAccount> primaryAccount=changePrimaryAccountRepository.getFormFromCSR(serviceRequestId);
            if (primaryAccount.isPresent()){
                changePrimaryAccount.setChangePrimaryAccountId(primaryAccount.get().getChangePrimaryAccountId());
            }

            if(!card_validation.checkCardValidity(Long.toString(changePrimaryAccountDTO.getCardNumber()))){
                res.setMessage("Invalid Card Details");
                res.setStatus(false);
                return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
            }

            if(!card_validation.checkCardValidity(Long.toString(changePrimaryAccountDTO.getCardNumber()))){
                res.setMessage("Invalid Card Details");
                res.setStatus(false);
                return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
            }

            changePrimaryAccount.setCardNumber(changePrimaryAccountDTO.getCardNumber());
            changePrimaryAccount.setAccountNumber(changePrimaryAccountDTO.getAccountNumber());
            changePrimaryAccount.setCustomerServiceRequest(customerServiceRequest);

            if (changePrimaryAccountRepository.save(changePrimaryAccount) != null) {

                serviceRequestCustomerLog.setDate(java.util.Calendar.getInstance().getTime());
                serviceRequestCustomerLog.setIdentification(customerServiceRequest.getCustomer().getIdentification());
                serviceRequestCustomerLog.setIp(request.getRemoteAddr());
                serviceRequestCustomerLog.setMessage("Request Form Successfully Saved To The System");
                boolean result = serviceRequestCustomerLogService.saveServiceRequestCustomerLog(serviceRequestCustomerLog);

                if (result) {
                    serviceRequestFormLog.setDigiFormId(customerServiceRequest.getServiceRequest().getDigiFormId());
                    serviceRequestFormLog.setCustomerId(customerServiceRequest.getCustomer().getCustomerId());
                    serviceRequestFormLog.setDate(java.util.Calendar.getInstance().getTime());
                    serviceRequestFormLog.setFromId(customerServiceRequest.getServiceRequest().getServiceRequestId());
                    serviceRequestFormLog.setIp(request.getRemoteAddr());
                    serviceRequestFormLog.setStatus(true);
                    serviceRequestFormLog.setMessage(" Request Form  Successfully Saved To The System");
                    serviceRequestFormLogService.saveServiceRequestFormLog(serviceRequestFormLog);
                }

                res.setMessage(" Request Successfully Saved To The System");
                res.setStatus(true);
                return new ResponseEntity<>(res, HttpStatus.CREATED);
            } else {

                serviceRequestCustomerLog.setDate(java.util.Calendar.getInstance().getTime());
                serviceRequestCustomerLog.setIdentification(customerServiceRequest.getCustomer().getIdentification());
                serviceRequestCustomerLog.setIp(request.getRemoteAddr());
                serviceRequestCustomerLog.setMessage("Failed TO Save The Request...  Operation Unsuccessful");
                boolean result = serviceRequestCustomerLogService.saveServiceRequestCustomerLog(serviceRequestCustomerLog);

                if (result) {
                    serviceRequestFormLog.setDigiFormId(customerServiceRequest.getServiceRequest().getDigiFormId());
                    serviceRequestFormLog.setCustomerId(customerServiceRequest.getCustomer().getCustomerId());
                    serviceRequestFormLog.setDate(java.util.Calendar.getInstance().getTime());
                    serviceRequestFormLog.setFromId(customerServiceRequest.getServiceRequest().getServiceRequestId());
                    serviceRequestFormLog.setIp(request.getRemoteAddr());
                    serviceRequestFormLog.setStatus(true);
                    serviceRequestFormLog.setMessage(" Failed TO Save The Request...   Operation  Unsuccessful");
                    serviceRequestFormLogService.saveServiceRequestFormLog(serviceRequestFormLog);
                }

                res.setMessage(" Failed TO Save The Request... Operation Unsuccessful");
                res.setStatus(false);
                return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
            }
        }
    }


}
