package com.spring.starter.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import com.spring.starter.configuration.ServiceRequestIdConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.spring.starter.DTO.MailingMailDTO;
import com.spring.starter.Repository.ChangeMailingMailModelRepository;
import com.spring.starter.Repository.CustomerServiceRequestRepository;
import com.spring.starter.Repository.MailingMailModelRepository;
import com.spring.starter.model.ChangeMailingMailModel;
import com.spring.starter.model.CustomerServiceRequest;
import com.spring.starter.model.MailingMailAccountNumbers;
import com.spring.starter.model.ResponseModel;
import com.spring.starter.service.MailingMailService;

@Service
@Transactional
public class MailingMailServiceImpl implements MailingMailService {

    @Autowired
    private CustomerServiceRequestRepository customerServiceRequestRepository;

    @Autowired
    private ChangeMailingMailModelRepository changeMailingMailModelRepository;

    @Autowired
    private MailingMailModelRepository mailingMailModelRepository;

    @Override
    public ResponseEntity<?> addchangeMailingMailRequest(MailingMailDTO mailingMailDTO, int customerServiceRequestId) {

        ResponseModel responsemodel = new ResponseModel();
        Optional<CustomerServiceRequest> customerServiceRequest = customerServiceRequestRepository.findById(customerServiceRequestId);
        if (!customerServiceRequest.isPresent()) {
            responsemodel.setMessage("There is No such service Available");
            responsemodel.setStatus(false);
            return new ResponseEntity<>(responsemodel, HttpStatus.NO_CONTENT);
        }
        int serviceRequestId = customerServiceRequest.get().getServiceRequest().getDigiFormId();
        if (serviceRequestId != ServiceRequestIdConfig.CHANGE_MAILING_ADDRESS) {
            responsemodel.setMessage("Invalied Request");
            responsemodel.setStatus(false);
            return new ResponseEntity<>(responsemodel, HttpStatus.BAD_REQUEST);
        }
        ChangeMailingMailModel changeMailingMailModel = new ChangeMailingMailModel();
        Optional<ChangeMailingMailModel> changeMailingMailOpt = changeMailingMailModelRepository.getFormFromCSR(customerServiceRequestId);
        if (changeMailingMailOpt.isPresent()) {
            changeMailingMailModel.setChangeMailingMailId(changeMailingMailOpt.get().getChangeMailingMailId());
        }
        List<MailingMailAccountNumbers> accountNumbers = new ArrayList<MailingMailAccountNumbers>();
        for (String s : mailingMailDTO.getMailingMailAccountNo()) {
            MailingMailAccountNumbers accountNumber = new MailingMailAccountNumbers();
            accountNumber.setAccountNo(s);
            accountNumber = mailingMailModelRepository.save(accountNumber);
            accountNumbers.add(accountNumber);
        }
        changeMailingMailModel.setMailingMailAccountNo(accountNumbers);
        changeMailingMailModel.setNewMailingAddress(mailingMailDTO.getNewMailingAddress());
        changeMailingMailModel.setCity(mailingMailDTO.getCity());
        changeMailingMailModel.setPostalCode(mailingMailDTO.getPostalCode());
        changeMailingMailModel.setStateOrProvince(mailingMailDTO.getStateOrProvince());
        changeMailingMailModel.setCustomerServiceRequest(customerServiceRequest.get());

        try {
            changeMailingMailModelRepository.save(changeMailingMailModel);
            responsemodel.setMessage("Service Saved Successfully");
            responsemodel.setStatus(true);
            return new ResponseEntity<>(responsemodel, HttpStatus.CREATED);
        } catch (Exception e) {
            responsemodel.setMessage("Something went wrong with the database Connection");
            responsemodel.setStatus(false);
            return new ResponseEntity<>(responsemodel, HttpStatus.SERVICE_UNAVAILABLE);
        }
    }
}
