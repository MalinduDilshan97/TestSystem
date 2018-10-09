package com.spring.starter.service.impl;

import com.spring.starter.Repository.CustomerTransactionRequestRepository;
import com.spring.starter.Repository.FundTransferSLIPRepository;
import com.spring.starter.configuration.TransactionIdConfig;
import com.spring.starter.model.CustomerTransactionRequest;
import com.spring.starter.model.FundTransferSLIPS;
import com.spring.starter.model.ResponseModel;
import com.spring.starter.service.FundTransferSLIPService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
public class FundTransferSLIPServiceImpl implements FundTransferSLIPService {

    @Autowired
    CustomerTransactionRequestRepository customerTransactionRequestRepository;

    @Autowired
    FundTransferSLIPRepository fundTransferSLIPRepository;

    @Override
    public ResponseEntity<?> addNewFundTransferSlipRequest(FundTransferSLIPS fundTransferSLIPS, int customerTransactionRequestId) throws Exception {
        ResponseModel responseModel = new ResponseModel();
        Optional<CustomerTransactionRequest> customerTransactionRequest = customerTransactionRequestRepository
                .findById(customerTransactionRequestId);
        if(!customerTransactionRequest.isPresent()){
            responseModel.setMessage("Invalied Transaction Request");
            responseModel.setStatus(false);
            return new ResponseEntity<>(responseModel,HttpStatus.BAD_REQUEST);
        }
        int id = customerTransactionRequest.get().getTransactionRequest().getDigiFormId();
        if(id != TransactionIdConfig.FUND_TRANSFER_TO_OTHER_BANKS_SLIP){
            responseModel.setMessage("Invalied Transaction Request Id");
            responseModel.setStatus(false);
            return new ResponseEntity<>(responseModel,HttpStatus.BAD_REQUEST);
        }
        Optional<FundTransferSLIPS> optionalTransferSLIPS = fundTransferSLIPRepository.getFormFromCSR(customerTransactionRequestId);
        if(optionalTransferSLIPS.isPresent()){
            fundTransferSLIPS.setFundTransferSLIPSId(optionalTransferSLIPS.get().getFundTransferSLIPSId());
        }
        fundTransferSLIPS.setCustomerTransactionRequest(customerTransactionRequest.get());
        try{
            fundTransferSLIPS = fundTransferSLIPRepository.save(fundTransferSLIPS);
            return new ResponseEntity<>(fundTransferSLIPS,HttpStatus.CREATED);
        } catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public ResponseEntity<?> getFundTransferSlipRequest(int fundTransferSlipID){
        Optional<FundTransferSLIPS> optionalTransferSLIPS = fundTransferSLIPRepository.findById(fundTransferSlipID);
        if(!optionalTransferSLIPS.isPresent()){
            ResponseModel responseModel = new ResponseModel();
            responseModel.setMessage("No content For that id");
            responseModel.setStatus(false);
            return new ResponseEntity<>(responseModel,HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(optionalTransferSLIPS,HttpStatus.OK);
        }
    }
}
