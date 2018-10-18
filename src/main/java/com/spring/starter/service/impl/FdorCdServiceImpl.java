package com.spring.starter.service.impl;

import com.spring.starter.DTO.WithholdingFdCdDTO;
import com.spring.starter.Repository.*;
import com.spring.starter.configuration.ServiceRequestIdConfig;
import com.spring.starter.model.*;
import com.spring.starter.service.FdorCdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseBody;


import java.util.*;

@Service
public class FdorCdServiceImpl implements FdorCdService {

    @Autowired
    private CustomerServiceRequestRepository customerServiceRequestRepository;

    @Autowired
    FdCdNumbersRepository fdCdNumbersRepository;
    @Autowired
    WithholdingFdCdRepository withholdingFdCdRepository;
    @Autowired
    OtherFdCdRelatedRequestRepository otherFdCdRelatedRequestRepository;
    @Autowired
    DuplicateFdCdCertRepository duplicateFdCdCertRepository;

    @Override
    public ResponseEntity<?> addWithHoldingTaxDC(WithholdingFdCdDTO fdCdNumbersDTO, int requestId) {
        WithholdingFdCd withholdingFdCd=new WithholdingFdCd();

        ResponseModel responsemodel = new ResponseModel();
        Optional<CustomerServiceRequest> customerServiceRequest = customerServiceRequestRepository.findById(requestId);
        if(!customerServiceRequest.isPresent()) {
            responsemodel.setMessage("There is No such service Available");
            responsemodel.setStatus(false);
            return new ResponseEntity<>(responsemodel, HttpStatus.NO_CONTENT);
        }
        int serviceRequestId = customerServiceRequest.get().getServiceRequest().getDigiFormId();
        if(serviceRequestId != ServiceRequestIdConfig.WITHHOLDING_TAX_DEDUCTION_CERTIFICATE)
        {
            responsemodel.setMessage("Invalid Request");
            responsemodel.setStatus(false);
            return new ResponseEntity<>(responsemodel, HttpStatus.BAD_REQUEST);
        }

        List<FdCdNumbers> numbersList1=new ArrayList<>();

        Optional<WithholdingFdCd> fdCdNumbersOptional=withholdingFdCdRepository.findByRequestId(requestId);
        if (fdCdNumbersOptional.isPresent()){
            withholdingFdCd.setWithholdingFdId(fdCdNumbersOptional.get().getWithholdingFdId());
            numbersList1=fdCdNumbersOptional.get().getFdCdNumbers();
            for (FdCdNumbers delnums:numbersList1){
                fdCdNumbersRepository.delete(delnums);
            }
        }

        List<FdCdNumbers> numbersList2=new ArrayList<>();
        for (String numbers:fdCdNumbersDTO.getFdCdNumbers()){
            FdCdNumbers num=new FdCdNumbers();
            num.setFdCdNumber(numbers);
            num=fdCdNumbersRepository.save(num);
            numbersList2.add(num);
        }


        withholdingFdCd.setMaturityDate(fdCdNumbersDTO.getMaturityDate());
        withholdingFdCd.setFdCdNumbers(numbersList2);
        withholdingFdCd.setCustomerServiceRequest(customerServiceRequest.get());
        withholdingFdCd.setAccountType(fdCdNumbersDTO.getAccountType());

        try{
            withholdingFdCdRepository.save(withholdingFdCd);
            responsemodel.setMessage("With holding numbers added successfully!");
            responsemodel.setStatus(true);
            return new ResponseEntity<>(responsemodel,HttpStatus.CREATED);
        }catch(Exception e){
            responsemodel.setMessage("With holding numbers added Failed!");
            responsemodel.setStatus(true);
            return new ResponseEntity<>(responsemodel,HttpStatus.BAD_REQUEST);
        }
    }
    @Override
    public ResponseEntity<?> addRelatedRequest(OtherFdCdRelatedRequest otherFdCdRelatedRequest,int requestId){
        ResponseModel responsemodel = new ResponseModel();
        Optional<CustomerServiceRequest> customerServiceRequest = customerServiceRequestRepository.findById(requestId);
        if(!customerServiceRequest.isPresent()) {
            responsemodel.setMessage("There is No such service Available");
            responsemodel.setStatus(false);
            return new ResponseEntity<>(responsemodel, HttpStatus.NO_CONTENT);
        }
        int serviceRequestId = customerServiceRequest.get().getServiceRequest().getDigiFormId();
        if(serviceRequestId != ServiceRequestIdConfig.OTHER_FD_CD_RELATED_REQUESTS)
        {
            responsemodel.setMessage("Invalid Request");
            responsemodel.setStatus(false);
            return new ResponseEntity<>(responsemodel, HttpStatus.BAD_REQUEST);
        }

        Optional<OtherFdCdRelatedRequest> otherFdCdRelatedRequestOptional=otherFdCdRelatedRequestRepository.findByRequestId(requestId);
        if (otherFdCdRelatedRequestOptional.isPresent()){
            otherFdCdRelatedRequest.setRelatedReqId(otherFdCdRelatedRequestOptional.get().getRelatedReqId());
        }
        TimeZone.setDefault(TimeZone.getTimeZone("UTC+5.30"));
        otherFdCdRelatedRequest.setDate(new Date());
        otherFdCdRelatedRequest.setCustomerServiceRequest(customerServiceRequest.get());

        try {
            otherFdCdRelatedRequest = otherFdCdRelatedRequestRepository.save(otherFdCdRelatedRequest);
            responsemodel.setMessage("Successfully created");
            responsemodel.setStatus(true);
            return new ResponseEntity<>(otherFdCdRelatedRequest, HttpStatus.CREATED);
        }catch (Exception e){
            responsemodel.setMessage("Error  in creating requests");
            responsemodel.setStatus(false);
            return new ResponseEntity<>(responsemodel, HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity<?> addDuplicateFdCdCert(DuplicateFdCdCert duplicateFdCdCert, int requestId) {
        ResponseModel responsemodel = new ResponseModel();
        Optional<CustomerServiceRequest> customerServiceRequest = customerServiceRequestRepository.findById(requestId);
        if(!customerServiceRequest.isPresent()) {
            responsemodel.setMessage("There is No such service Available");
            responsemodel.setStatus(false);
            return new ResponseEntity<>(responsemodel, HttpStatus.NO_CONTENT);
        }
        int serviceRequestId = customerServiceRequest.get().getServiceRequest().getDigiFormId();
        if(serviceRequestId != ServiceRequestIdConfig.DUPLICATE_FD_CD_CERTIFICATE)
        {
            responsemodel.setMessage("Invalid Request");
            responsemodel.setStatus(false);
            return new ResponseEntity<>(responsemodel, HttpStatus.BAD_REQUEST);
        }

        Optional<DuplicateFdCdCert> duplicateFdCdCertOptional=duplicateFdCdCertRepository.findByRequestId(requestId);
        if (duplicateFdCdCertOptional.isPresent()){
            duplicateFdCdCert.setDuplicateId(duplicateFdCdCertOptional.get().getDuplicateId());
        }
        TimeZone.setDefault(TimeZone.getTimeZone("UTC+5.30"));
        duplicateFdCdCert.setDate(new Date());
        duplicateFdCdCert.setCustomerServiceRequest(customerServiceRequest.get());
        try{
            duplicateFdCdCert = duplicateFdCdCertRepository.save(duplicateFdCdCert);
            responsemodel.setMessage("Successfully added duplicate fd/cd");
            responsemodel.setStatus(true);
            return new ResponseEntity<>(duplicateFdCdCert, HttpStatus.CREATED);
        }catch (Exception e){
        responsemodel.setMessage("Error  in creating duplicate fd/cd");
        responsemodel.setStatus(false);
        return new ResponseEntity<>(responsemodel, HttpStatus.BAD_REQUEST);
    }

    }


}
