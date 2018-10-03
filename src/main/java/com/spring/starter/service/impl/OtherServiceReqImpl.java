package com.spring.starter.service.impl;

import com.spring.starter.Repository.CustomerServiceRequestRepository;
import com.spring.starter.Repository.OtherServiceRequestRepository;
import com.spring.starter.configuration.ServiceRequestIdConfig;
import com.spring.starter.model.CustomerServiceRequest;
import com.spring.starter.model.OtherServiceRequest;
import com.spring.starter.model.ResponseModel;
import com.spring.starter.service.OtherServiceReqService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;
import java.util.TimeZone;

@Service
public class OtherServiceReqImpl implements OtherServiceReqService {

    @Autowired
    CustomerServiceRequestRepository customerServiceRequestRepository;
    @Autowired
    OtherServiceRequestRepository otherServiceRequestRepository;




    @Override
    public ResponseEntity<?> addOtherRequest(OtherServiceRequest otherServiceRequest, int requestId) {
        ResponseModel responsemodel = new ResponseModel();
        Optional<CustomerServiceRequest> customerServiceRequest = customerServiceRequestRepository.findById(requestId);
        if(!customerServiceRequest.isPresent()) {
            responsemodel.setMessage("There is No such service Available");
            responsemodel.setStatus(false);
            return new ResponseEntity<>(responsemodel, HttpStatus.NO_CONTENT);
        }
        int serviceRequestId = customerServiceRequest.get().getServiceRequest().getDigiFormId();
        if(serviceRequestId != ServiceRequestIdConfig.OTHER)
        {
            responsemodel.setMessage("Invalid Request");
            responsemodel.setStatus(false);
            return new ResponseEntity<>(responsemodel, HttpStatus.BAD_REQUEST);
        }

        Optional<OtherServiceRequest> otherFdCdRelatedRequestOptional=otherServiceRequestRepository.findByRequestId(requestId);
        if (otherFdCdRelatedRequestOptional.isPresent()){
            otherServiceRequest.setOtherid(otherFdCdRelatedRequestOptional.get().getOtherid());
        }
        TimeZone.setDefault(TimeZone.getTimeZone("UTC+5.30"));
        otherServiceRequest.setDate(new Date());
        otherServiceRequest.setCustomerServiceRequest(customerServiceRequest.get());

        try {
            otherServiceRequestRepository.save(otherServiceRequest);
            responsemodel.setMessage("Successfully added other request");
            responsemodel.setStatus(true);
            return new ResponseEntity<>(responsemodel, HttpStatus.CREATED);
        }catch (Exception e){
            responsemodel.setMessage("Error  in creating requests");
            responsemodel.setStatus(false);
            return new ResponseEntity<>(responsemodel, HttpStatus.BAD_REQUEST);
        }
    }
}
