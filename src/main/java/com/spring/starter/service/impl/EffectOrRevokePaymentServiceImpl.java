package com.spring.starter.service.impl;

import com.spring.starter.DTO.EffectOrRevokePaymentDTO;
import com.spring.starter.DTO.EffectOrRevokePaymentDetailsDTO;
import com.spring.starter.Exception.CustomException;
import com.spring.starter.Repository.*;
import com.spring.starter.configuration.ServiceRequestIdConfig;
import com.spring.starter.model.*;
import com.spring.starter.service.EffectOrRevokePaymentService;
import com.spring.starter.service.ServiceRequestCustomerLogService;
import com.spring.starter.service.ServiceRequestFormLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Transactional
@Service
public class EffectOrRevokePaymentServiceImpl implements EffectOrRevokePaymentService {

    @Autowired
    private EffectOrRevokePaymentRepository effectOrRevokePaymentRepository;
    @Autowired
    private EffectOrRevokePaymentDetailsRepository effectOrRevokePaymentDetailsRepository;
    @Autowired
    private CustomerServiceRequestRepository customerServiceRequestRepository;
    @Autowired
    private ServiceRequestCustomerLogService serviceRequestCustomerLogService;
    @Autowired
    private ServiceRequestFormLogService serviceRequestFormLogService;

    private ResponseModel res = new ResponseModel();
    DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    public ResponseEntity<?> saveEffectOrPaymentRequest(EffectOrRevokePaymentDTO effectOrRevokePaymentDTO,
                                                        HttpServletRequest request) {

        ResponseModel responsemodel = new ResponseModel();
        ServiceRequestCustomerLog serviceRequestCustomerLog = new ServiceRequestCustomerLog();
        ServiceRequestFormLog serviceRequestFormLog = new ServiceRequestFormLog();

        Optional<CustomerServiceRequest> customerServiceRequestOpt = customerServiceRequestRepository
                .findById(effectOrRevokePaymentDTO.getCustomerServiceRequestId());
        if(!customerServiceRequestOpt.isPresent()) {
            responsemodel.setMessage("There is No such service Available");
            responsemodel.setStatus(false);
            return new ResponseEntity<>(responsemodel, HttpStatus.NO_CONTENT);
        }



        int serviceRequestId = customerServiceRequestOpt.get().getServiceRequest().getDigiFormId();

        if(serviceRequestId != ServiceRequestIdConfig.STOP_REVOKE_PAYMENT)
        {
            responsemodel.setMessage("Invalided Request");
            responsemodel.setStatus(false);
            return new ResponseEntity<>(responsemodel, HttpStatus.BAD_REQUEST);
        }


        List<EffectOrRevokePaymentDetails> effectOrRevokePaymentDetails = new ArrayList<>();

        Optional<EffectOrRevokePayment> optionalPayment = effectOrRevokePaymentRepository
                .getFormFromCSR(effectOrRevokePaymentDTO.getCustomerServiceRequestId());
        EffectOrRevokePayment effectOrRevokePayment = new EffectOrRevokePayment();

        if(optionalPayment.isPresent()){
            effectOrRevokePayment.setEffectOrRevokePaymentId(optionalPayment.get().getEffectOrRevokePaymentId());

            if(!optionalPayment.get().getEffectOrRevokePaymentDetails().isEmpty()){

                for(EffectOrRevokePaymentDetails effectOrRevokePaymentDetails1 : optionalPayment.get().getEffectOrRevokePaymentDetails()){
                    effectOrRevokePaymentDetailsRepository.delete(effectOrRevokePaymentDetails1);
                }
            }
        }

        CustomerServiceRequest customerServiceRequest = customerServiceRequestOpt.get();

        effectOrRevokePayment.setCustomerAccountNo(effectOrRevokePaymentDTO.getAccountNo());
        effectOrRevokePayment.setCustomerServiceRequest(customerServiceRequest);
        effectOrRevokePayment.setStatus(effectOrRevokePaymentDTO.getStatus());

        effectOrRevokePayment = effectOrRevokePaymentRepository.save(effectOrRevokePayment);


        if (effectOrRevokePayment != null) {
            for (EffectOrRevokePaymentDetailsDTO dto : effectOrRevokePaymentDTO.getList()) {
                try {
                    Date chequeDate = df.parse(dto.getDateOfCheque());

                    EffectOrRevokePaymentDetails details = new EffectOrRevokePaymentDetails();
                    details.setChequeNumber(dto.getChequeNumber());
                    details.setValue(dto.getValue());
                    details.setPayeeName(dto.getPayeeName());
                    details.setDateOfCheque(chequeDate);
                    details.setReason(dto.getReason());
                    details.setEffectOrRevokePayment(effectOrRevokePayment);

                    details =  effectOrRevokePaymentDetailsRepository.save(details);
                    effectOrRevokePaymentDetails.add(details);

                } catch (ParseException e) {
                    throw new CustomException("Failed TO Save The Request... Operation Unsuccessful" +
                            " Input Date To This Format (YYYY-MM-DD)");
                }
            }


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
                serviceRequestFormLog.setMessage("Request Form Successfully Saved To The System");
                serviceRequestFormLogService.saveServiceRequestFormLog(serviceRequestFormLog);
            }

            effectOrRevokePayment.setEffectOrRevokePaymentDetails(effectOrRevokePaymentDetails);
            effectOrRevokePayment = effectOrRevokePaymentRepository.save(effectOrRevokePayment);
            res.setMessage(" Request Form Successfully Saved To The System");
            res.setStatus(true);
            return new ResponseEntity<>(effectOrRevokePayment, HttpStatus.CREATED);

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
                serviceRequestFormLog.setMessage(" Failed TO Save The Request... Operation Unsuccessful");
                serviceRequestFormLogService.saveServiceRequestFormLog(serviceRequestFormLog);
            }

            res.setMessage(" Failed TO Save The Request... Operation Unsuccessful");
            res.setStatus(false);
            return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
        }
    }
}
