package com.spring.starter.service.impl;

import com.spring.starter.Exception.CustomException;
import com.spring.starter.Repository.CashdepositRepositiry;
import com.spring.starter.Repository.CustomerTransactionRequestRepository;
import com.spring.starter.configuration.TransactionIdConfig;
import com.spring.starter.model.CashDeposit;
import com.spring.starter.model.CustomerTransactionRequest;
import com.spring.starter.model.ResponseModel;
import com.spring.starter.service.CashDepositService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
public class CashDepositServiceImpl implements CashDepositService {

    @Autowired
    CustomerTransactionRequestRepository customerTransactionRequestRepository;

    @Autowired
    CashdepositRepositiry cashdepositRepositiry;

    @Override
    public ResponseEntity<?> saveNewCashDepositRequest(CashDeposit cashDeposit, int customerTransactionRequestId) {
        ResponseModel responseModel = new ResponseModel();
        Optional<CustomerTransactionRequest> customerTransactionRequest = customerTransactionRequestRepository
                .findById(customerTransactionRequestId);
        if(!customerTransactionRequest.isPresent()){
            responseModel.setMessage("Invalied Transaction Request");
            responseModel.setStatus(false);
            return new ResponseEntity<>(responseModel,HttpStatus.BAD_REQUEST);
        }
        int id = customerTransactionRequest.get().getTransactionRequest().getDigiFormId();
        if(id != TransactionIdConfig.WITHDRAWALS){
            responseModel.setMessage("Invalied Transaction Request Id");
            responseModel.setStatus(false);
            return new ResponseEntity<>(responseModel,HttpStatus.BAD_REQUEST);
        }
        Optional<CashDeposit> cashDepositOpt = cashdepositRepositiry.getFormFromCSR(customerTransactionRequestId);
        if(cashDepositOpt.isPresent()){
            cashDeposit.setCashDepositId(cashDepositOpt.get().getCashDepositId());
        }
        cashDeposit.setCustomerTransactionRequest(customerTransactionRequest.get());

            if (cashDeposit.getValueOf10Notes() == 0 && cashDeposit.getValueOf20Notes() == 0 &&
                    cashDeposit.getValueOf50Notes() == 0 && cashDeposit.getValueOf100Notes() == 0 &&
                    cashDeposit.getValueOf500Notes() == 0 && cashDeposit.getValueof1000Notes() == 0 &&
                    cashDeposit.getValueOf2000Notes() == 0 && cashDeposit.getValueOf2000Notes() == 0) {
                responseModel.setMessage("Please fill cash details");
                responseModel.setStatus(false);
                return new ResponseEntity<>(responseModel, HttpStatus.BAD_REQUEST);
            }

        double sum = (double) (cashDeposit.getValueOf5000Notes() + cashDeposit.getValueOf2000Notes()
                + cashDeposit.getValueof1000Notes() + cashDeposit.getValueOf100Notes() +
                cashDeposit.getValueOf500Notes() + cashDeposit.getValueOf50Notes() +
                cashDeposit.getValueOf20Notes() + cashDeposit.getValueOf10Notes() +
                cashDeposit.getValueOfcoins());
        if(sum != cashDeposit.getTotal()){
            responseModel.setMessage("Incorrect Cash Total");
            responseModel.setStatus(false);
            return new ResponseEntity<>(responseModel,HttpStatus.BAD_REQUEST);
        }
        try{
            cashDeposit =  cashdepositRepositiry.save(cashDeposit);
            return new ResponseEntity<>(cashDeposit,HttpStatus.CREATED);
        } catch (Exception e) {
            throw new CustomException(e.getMessage());
        }
    }

    @Override
    public ResponseEntity<?> getCashDepositRequest(int cashDepositId){
        Optional<CashDeposit> cashDeposit = cashdepositRepositiry.findById(cashDepositId);
        if(!cashDeposit.isPresent()){
            ResponseModel responseModel = new ResponseModel();
            responseModel.setMessage("There is no record for that Id");
            responseModel.setStatus(false);
            return new ResponseEntity<>(responseModel,HttpStatus.NO_CONTENT);
        }else {
            return new ResponseEntity<>(cashDeposit,HttpStatus.OK);
        }
    }
}
