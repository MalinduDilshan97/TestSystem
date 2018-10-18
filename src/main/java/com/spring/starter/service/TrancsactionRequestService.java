package com.spring.starter.service;


import com.spring.starter.model.TransactionCustomer;
import com.spring.starter.model.TransactionRequest;
import org.springframework.http.ResponseEntity;

public interface TrancsactionRequestService {

    public ResponseEntity<?> addNewServiceRequest(TransactionRequest transactionRequest);

    public ResponseEntity<?> getBankServices();

    public ResponseEntity<?> addCustomer(TransactionCustomer transactionCustomer);

    public ResponseEntity<?> getAllTransactionCustomerDetails();

    public ResponseEntity<?> getOneCustomerDetails(int id);

    public ResponseEntity<?> getCustomerDetailsFormIndentity(String identity);

    public ResponseEntity<?> updateTransactionCustomerDetails(TransactionCustomer transactionCustomer , int recordId);

    public ResponseEntity<?> getCustomerDetailsOfAIdentityFilterByDate(String identity, String date);

    public ResponseEntity<?> getTransactionCustomerDetailsFilterByDate(String date);

    public ResponseEntity<?> addCustomerToATransactionRequest(int transactionCustomerId , int trasactionRequestId);

    public ResponseEntity<?> getAllCustomerTransactionRequests(int customerId);

    public ResponseEntity<?> getAllCustomerTransactionRequestsFilterByDate(int customerId, String date);

    public ResponseEntity<?> viewTransactionRequest (int customerTransactionRequest);
}
