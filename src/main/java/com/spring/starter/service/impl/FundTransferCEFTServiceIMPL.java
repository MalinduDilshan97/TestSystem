package com.spring.starter.service.impl;

import com.spring.starter.Repository.BankRepository;
import com.spring.starter.Repository.BranchRepository;
import com.spring.starter.Repository.CustomerTransactionRequestRepository;
import com.spring.starter.Repository.FundTransferCEFTRepository;
import com.spring.starter.configuration.TransactionIdConfig;
import com.spring.starter.model.*;
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

    @Autowired
    BankRepository bankRepository;

    @Autowired
    BranchRepository branchRepository;


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

        Optional<Bank> bank = bankRepository.findById(fundTransferCEFT.getBank().getMx_bank_code());
        if(!bank.isPresent()){
            responseModel.setMessage("invalied bank details");
            responseModel.setStatus(false);
            return new ResponseEntity<>(responseModel,HttpStatus.BAD_REQUEST);
        } else {
            fundTransferCEFT.setBank(bank.get());
        }

        Optional<Branch> branch = branchRepository.findById(fundTransferCEFT.getBranch().getBranch_id());
        if(!branch.isPresent()){
            responseModel.setMessage("invalied bank branch details");
            responseModel.setStatus(false);
            return new ResponseEntity<>(responseModel,HttpStatus.BAD_REQUEST);
        }  else {
            fundTransferCEFT.setBranch(branch.get());
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
