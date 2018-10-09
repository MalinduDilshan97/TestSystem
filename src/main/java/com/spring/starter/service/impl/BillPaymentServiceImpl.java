package com.spring.starter.service.impl;

import com.spring.starter.Repository.BillPaymentRepository;
import com.spring.starter.Repository.CustomerTransactionRequestRepository;
import com.spring.starter.configuration.TransactionIdConfig;
import com.spring.starter.model.BillPayment;
import com.spring.starter.model.CustomerTransactionRequest;
import com.spring.starter.model.ResponseModel;
import com.spring.starter.service.BillPaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
public class BillPaymentServiceImpl implements BillPaymentService {

    @Autowired
    BillPaymentRepository billPaymentRepository;

    @Autowired
    CustomerTransactionRequestRepository customerTransactionRequestRepository;

    @Override
    public ResponseEntity<?> saveBillPayment(BillPayment billPayment, int customerTransactionRequestId) {
        ResponseModel responseModel = new ResponseModel();
       Optional<CustomerTransactionRequest> customerTransactionRequest = customerTransactionRequestRepository
                                                                                .findById(customerTransactionRequestId);
       if(!customerTransactionRequest.isPresent()){
            responseModel.setMessage("Invalied Transaction Request");
            responseModel.setStatus(false);
            return new ResponseEntity<>(responseModel,HttpStatus.BAD_REQUEST);
       }
       int id = customerTransactionRequest.get().getTransactionRequest().getDigiFormId();
       if(id != TransactionIdConfig.BILLPAYMENT){
           responseModel.setMessage("Invalied Transaction Request Id");
           responseModel.setStatus(false);
           return new ResponseEntity<>(responseModel,HttpStatus.BAD_REQUEST);
       }
       Optional<BillPayment> billPaymentOptional = billPaymentRepository.getFormFromCSR(customerTransactionRequestId);
       if(billPaymentOptional.isPresent()){
            billPayment.setBillPaymentId(billPaymentOptional.get().getBillPaymentId());
       }
       billPayment.setCustomerTransactionRequest(customerTransactionRequest.get());
       if(billPayment.isCurrencyIsCash()) {
           if (billPayment.getValueOf10Notes() == 0 && billPayment.getValueOf20Notes() == 0 &&
                   billPayment.getValueOf50Notes() == 0 && billPayment.getValueOf100Notes() == 0 &&
                   billPayment.getValueOf500Notes() == 0 && billPayment.getValueof1000Notes() == 0 &&
                   billPayment.getValueOf2000Notes() == 0 && billPayment.getValueOf2000Notes() == 0) {
               responseModel.setMessage("Please fill cash details");
               responseModel.setStatus(false);
               return new ResponseEntity<>(responseModel, HttpStatus.BAD_REQUEST);
           }
       }
       double sum = (double) (billPayment.getValueOf5000Notes() + billPayment.getValueOf2000Notes()
                      + billPayment.getValueof1000Notes() + billPayment.getValueOf100Notes() +
                      billPayment.getValueOf500Notes() + billPayment.getValueOf50Notes() +
                      billPayment.getValueOf20Notes() + billPayment.getValueOf10Notes() +
                      billPayment.getValueOfcoins());
       if(sum != billPayment.getTotal()){
           responseModel.setMessage("Incorrect Cash Total");
           responseModel.setStatus(false);
           return new ResponseEntity<>(responseModel,HttpStatus.BAD_REQUEST);
       }
       try{
           responseModel.setMessage("saved successfully");
           responseModel.setStatus(true);
           billPayment = billPaymentRepository.save(billPayment);
           return new ResponseEntity<>(responseModel,HttpStatus.CREATED);
       } catch (Exception e) {
           responseModel.setMessage("Something Went wrong");
           responseModel.setStatus(false);
           return new ResponseEntity<>(responseModel,HttpStatus.BAD_REQUEST);
       }
    }

    @Override
    public ResponseEntity<?> getBillPaymentRequest(int billPaymentId){
        Optional<BillPayment> billPaymentOptional = billPaymentRepository.findById(billPaymentId);
        if(!billPaymentOptional.isPresent()){
            ResponseModel responseModel = new ResponseModel();
            responseModel.setMessage("There is no bill payment details available for that id");
            responseModel.setStatus(false);
            return new ResponseEntity<>(responseModel,HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(billPaymentOptional,HttpStatus.OK);
        }

    }
}
