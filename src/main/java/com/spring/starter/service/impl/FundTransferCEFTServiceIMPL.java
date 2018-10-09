package com.spring.starter.service.impl;

import com.spring.starter.Repository.CustomerTransactionRequestRepository;
import com.spring.starter.Repository.FundTransferCEFTRepository;
import com.spring.starter.configuration.TransactionIdConfig;
import com.spring.starter.model.CustomerTransactionRequest;
import com.spring.starter.model.FundTransferCEFT;
import com.spring.starter.model.ResponseModel;
import com.spring.starter.service.FundTransferCEFTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
public class FundTransferCEFTServiceIMPL implements FundTransferCEFTService {

    @Autowired
    CustomerTransactionRequestRepository customerTransactionRequestRepository;

    @Autowired
    FundTransferCEFTRepository fundTransferCEFTRepository;


    @Override
    public ResponseEntity<?> addNewFundTransferCEFTRequest(FundTransferCEFT fundTransferCEFT, int customerTransactionRequestId) throws Exception {
        ResponseModel responseModel = new ResponseModel();
        Optional<CustomerTransactionRequest> customerTransactionRequest = customerTransactionRequestRepository
                .findById(customerTransactionRequestId);
        if(!customerTransactionRequest.isPresent()){
            responseModel.setMessage("Invalied Transaction Request");
            responseModel.setStatus(false);
            return new ResponseEntity<>(responseModel,HttpStatus.BAD_REQUEST);
        }
        int id = customerTransactionRequest.get().getTransactionRequest().getDigiFormId();
        if(id != TransactionIdConfig.FUND_TRANSFER_TO_OTHER_BANKS_CEFT){
            responseModel.setMessage("Invalied Transaction Request Id");
            responseModel.setStatus(false);
            return new ResponseEntity<>(responseModel,HttpStatus.BAD_REQUEST);
        }
        Optional<FundTransferCEFT> optionalTransferCEFT = fundTransferCEFTRepository.getFormFromCSR(customerTransactionRequestId);
        if(optionalTransferCEFT.isPresent()){
            fundTransferCEFT.setFundTransferCEFTId(optionalTransferCEFT.get().getFundTransferCEFTId());
        }
        fundTransferCEFT.setCustomerTransactionRequest(customerTransactionRequest.get());
        try{
            fundTransferCEFT = fundTransferCEFTRepository.save(fundTransferCEFT);
            return new ResponseEntity<>(fundTransferCEFT,HttpStatus.CREATED);
        } catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public ResponseEntity<?> getFundTransferCEFTRequest(int fundTransferCEFTID) {
        Optional<FundTransferCEFT> optionalTransferCEFT = fundTransferCEFTRepository.findById(fundTransferCEFTID);
        if(!optionalTransferCEFT.isPresent()){
            ResponseModel responseModel = new ResponseModel();
            responseModel.setMessage("No content For that id.");
            responseModel.setStatus(false);
            return new ResponseEntity<>(responseModel,HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(optionalTransferCEFT,HttpStatus.OK);
        }
    }
}
