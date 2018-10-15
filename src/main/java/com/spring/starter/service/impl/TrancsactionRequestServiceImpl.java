package com.spring.starter.service.impl;

import com.spring.starter.Exception.CustomException;
import com.spring.starter.Repository.CustomerTransactionRequestRepository;
import com.spring.starter.Repository.TrancsactionRequestServiceRepository;
import com.spring.starter.Repository.TransactionCustomerRepository;
import com.spring.starter.model.*;
import com.spring.starter.service.TrancsactionRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class TrancsactionRequestServiceImpl implements TrancsactionRequestService {

    @Autowired
    TrancsactionRequestServiceRepository trancsactionRequestServiceRepository;

    @Autowired
    TransactionCustomerRepository transactionCustomerRepository;

    @Autowired
    CustomerTransactionRequestRepository customerTransactionRequestRepository;


    @Override
    public ResponseEntity<?> addNewServiceRequest(TransactionRequest transactionRequest) {

        ResponseModel responseModel = new ResponseModel();
        try{
            transactionRequest.setDate(java.util.Calendar.getInstance().getTime());
            transactionRequest = trancsactionRequestServiceRepository.save(transactionRequest);
        } catch (Exception e) {
            responseModel.setMessage(e.getMessage());
            responseModel.setStatus(false);
            return new ResponseEntity<>(responseModel,HttpStatus.SERVICE_UNAVAILABLE);
        }
        responseModel.setMessage("Transaction Service Created Successfully");
        responseModel.setStatus(true);

        return new ResponseEntity<>(transactionRequest,HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<?> getBankServices() {
        ResponseModel responseModel = new ResponseModel();
        try {
            List<TransactionRequest> transactionRequests = trancsactionRequestServiceRepository.findAll();
            if(transactionRequests.isEmpty()){
                responseModel.setMessage("No Bank Transctional services added yet");
                responseModel.setStatus(false);
                return new ResponseEntity<>(responseModel,HttpStatus.NO_CONTENT);
            } else {
                return new ResponseEntity<>(transactionRequests,HttpStatus.OK);
            }
        }catch (Exception e){
            responseModel.setMessage("Something Went Wrong with the DB Connection");
            responseModel.setStatus(false);
            return new ResponseEntity<>(responseModel,HttpStatus.SERVICE_UNAVAILABLE);
        }

    }
    @Override
    public ResponseEntity<?> addCustomer(TransactionCustomer transactionCustomer){
        ResponseModel responseModel = new ResponseModel();
        String num =  transactionCustomer.getMobile();
         if(num.length() == 10){
            num = num.substring(1,10);
            char a_char = num.charAt(0);
            if(a_char != '7'){
                responseModel.setMessage("Please Insert A Correct Mobile number");
                responseModel.setStatus(false);
                return new ResponseEntity<>(responseModel,HttpStatus.BAD_REQUEST);
            }
            transactionCustomer.setMobile(num);
        } else if(num.length() == 9){
             char a_char = num.charAt(0);
             if(a_char != '7'){
                 responseModel.setMessage("Plese Insert A Correct Mobile number");
                 responseModel.setStatus(false);
                 return new ResponseEntity<>(responseModel,HttpStatus.BAD_REQUEST);
             }
         }
        try{
            transactionCustomer.setDate(java.util.Calendar.getInstance().getTime());
            transactionCustomer =transactionCustomerRepository.save(transactionCustomer);
            return new ResponseEntity<>(transactionCustomer,HttpStatus.CREATED);
        } catch (Exception e) {
            responseModel.setMessage("Something Went Wrong with the DB Connection");
            responseModel.setStatus(false);
            return new ResponseEntity<>(responseModel,HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

    @Override
    public ResponseEntity<?> updateTransactionCustomerDetails(TransactionCustomer transactionCustomer , int recordId){
        ResponseModel responseModel = new ResponseModel();
        Optional<TransactionCustomer> optionalCustomer = transactionCustomerRepository.findById(recordId);
        if(!optionalCustomer.isPresent()){
            responseModel.setMessage("There is No such record in the database");
            responseModel.setStatus(false);
            return new ResponseEntity<>(responseModel,HttpStatus.NO_CONTENT);
        } else {
            transactionCustomer.setTransactionCustomerId(optionalCustomer.get().getTransactionCustomerId());
            try{
                transactionCustomer = transactionCustomerRepository.save(transactionCustomer);
                return new ResponseEntity<>(transactionCustomer,HttpStatus.CREATED);
            } catch (Exception e){
                responseModel.setMessage("There is problem with the database connection");
                responseModel.setStatus(false);
                return new ResponseEntity<>(responseModel,HttpStatus.SERVICE_UNAVAILABLE);
            }
        }
    }

    @Override
    public ResponseEntity<?> getAllTransactionCustomerDetails(){
        ResponseModel responseModel = new ResponseModel();
        List<TransactionCustomer> transactionCustomers = transactionCustomerRepository.findAll();
        if(transactionCustomers.isEmpty()){
            responseModel.setMessage("No Records yet");
            responseModel.setStatus(false);
            return new ResponseEntity<>(responseModel,HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(transactionCustomers,HttpStatus.OK);
        }
    }
    @Override
    public ResponseEntity<?> getOneCustomerDetails(int id){
        ResponseModel responseModel = new ResponseModel();
        Optional<TransactionCustomer> transactionCustomer = transactionCustomerRepository.findById(id);
        if(!transactionCustomer.isPresent()){
            responseModel.setMessage("There Is No Such Record In The Database");
            responseModel.setStatus(false);
            return new ResponseEntity<>(responseModel,HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(transactionCustomer,HttpStatus.OK);
        }
    }
    @Override
    public ResponseEntity<?> getCustomerDetailsFormIndentity(String identity){
        ResponseModel responseModel = new ResponseModel();
        List<TransactionCustomer> transactionCustomer = transactionCustomerRepository.getRecordFromIdentity(identity);
        if(transactionCustomer.isEmpty()){
            responseModel.setMessage("There Is No Such Record In The Database.");
            responseModel.setStatus(false);
            return new ResponseEntity<>(responseModel,HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(transactionCustomer,HttpStatus.OK);
        }
    }

    @Override
    public ResponseEntity<?> getCustomerDetailsOfAIdentityFilterByDate(String identity, String date){
        ResponseModel responsemodel = new ResponseModel();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date requestDate;
        List<TransactionCustomer> transactionalCustomers = new ArrayList<>();
        try {
            requestDate = df.parse(date);
            transactionalCustomers = transactionCustomerRepository.getTransactionCustomerFilterBydate(identity,requestDate);
            if(transactionalCustomers.isEmpty()){
                responsemodel.setMessage("There is No Data For that Identity");
                responsemodel.setStatus(false);
                return new ResponseEntity<>(responsemodel,HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(transactionalCustomers,HttpStatus.OK);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        responsemodel.setMessage("Something Went Wrong");
        responsemodel.setStatus(false);
        return new ResponseEntity<>(responsemodel,HttpStatus.SERVICE_UNAVAILABLE);
    }

    @Override
    public ResponseEntity<?> getTransactionCustomerDetailsFilterByDate(String date){
        ResponseModel responsemodel = new ResponseModel();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date requestDate;
        List<TransactionCustomer> transactionalCustomers = new ArrayList<>();
        try {
            requestDate = df.parse(date);
            transactionalCustomers = transactionCustomerRepository.getTransactionsOfadate(requestDate);
            if(transactionalCustomers.isEmpty()){
                responsemodel.setMessage("There is No Data For that Identity");
                responsemodel.setStatus(false);
                return new ResponseEntity<>(responsemodel,HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(transactionalCustomers,HttpStatus.OK);
        }catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        responsemodel.setMessage("Something Went Wrong");
        responsemodel.setStatus(false);
        return new ResponseEntity<>(responsemodel,HttpStatus.SERVICE_UNAVAILABLE);
    }

    @Override
    public ResponseEntity<?> addCustomerToATransactionRequest(int transactionCustomerId , int trasactionRequestId){

        ResponseModel responseModel = new ResponseModel();
        Optional<TransactionCustomer> optionalCustomer = transactionCustomerRepository.findById(transactionCustomerId);
        Optional<TransactionRequest> optionalTransactionRequest = trancsactionRequestServiceRepository
                                                                                        .findById(trasactionRequestId);
        if(!optionalCustomer.isPresent()){
            responseModel.setStatus(false);
            responseModel.setMessage("There is no such transaction customer detail exists");
            return new ResponseEntity<>(responseModel,HttpStatus.NO_CONTENT);
        } else if(!optionalTransactionRequest.isPresent()) {
            responseModel.setStatus(false);
            responseModel.setMessage("Invalied Transaction Service Details");
            return new ResponseEntity<>(responseModel,HttpStatus.NO_CONTENT);
        }
        CustomerTransactionRequest customerTransactionRequest = new CustomerTransactionRequest();
        customerTransactionRequest.setTransactionRequest(optionalTransactionRequest.get());
        customerTransactionRequest.setTransactionCustomer(optionalCustomer.get());
        customerTransactionRequest.setRequestDate(java.util.Calendar.getInstance().getTime());
        customerTransactionRequest =  customerTransactionRequestRepository.save(customerTransactionRequest);

        return new ResponseEntity<>(customerTransactionRequest,HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<?> getAllCustomerTransactionRequests(int customerId){
        ResponseModel responseModel = new ResponseModel();
        Optional<TransactionCustomer> optionalCustomer =transactionCustomerRepository.findById(customerId);
        List<CustomerTransactionRequest> customerTransactionRequests  =customerTransactionRequestRepository.getAllTransactionCustomerRequest(customerId);
        if(!optionalCustomer.isPresent()){
            responseModel.setStatus(false);
            responseModel.setMessage("There is such customer record for transaction request");
            return new ResponseEntity<>(responseModel,HttpStatus.NO_CONTENT);
        } else if (customerTransactionRequests.isEmpty()){
            responseModel.setStatus(false);
            responseModel.setMessage("There is such transaction requests for "+optionalCustomer.get().getIdentification());
            return new ResponseEntity<>(responseModel,HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(customerTransactionRequests,HttpStatus.OK);
        }
    }

    @Override
    public ResponseEntity<?> getAllCustomerTransactionRequestsFilterByDate(int customerId, String date){
        ResponseModel responsemodel = new ResponseModel();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date requestDate;
        List<CustomerServiceRequest> customerServiceRequests = new ArrayList<>();
        try {
            requestDate = df.parse(date);
            List<CustomerTransactionRequest> transactionRequests = customerTransactionRequestRepository.getAllTransactionCustomerRequestsFilterBudate(customerId,requestDate);
            if(transactionRequests.isEmpty()){
                responsemodel.setMessage("There is no data present in the database");
                responsemodel.setStatus(false);
                return new ResponseEntity<>(responsemodel, HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(transactionRequests,HttpStatus.OK);
        } catch (Exception e) {
            throw new CustomException("invalied date format");
        }
    }

}
