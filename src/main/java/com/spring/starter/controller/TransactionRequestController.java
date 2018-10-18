package com.spring.starter.controller;

import com.spring.starter.DTO.CustomerTransactionDTO;
import com.spring.starter.model.TransactionCustomer;
import com.spring.starter.model.TransactionRequest;
import com.spring.starter.service.TrancsactionRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/Transaction-Request")
public class TransactionRequestController {

    @Autowired
    private TrancsactionRequestService trancsactionRequestService;

    @PostMapping("/add-service")
    public ResponseEntity<?> addNewTransactionService(@RequestBody @Valid TransactionRequest transactionRequest){
        return trancsactionRequestService.addNewServiceRequest(transactionRequest);
    }

    @GetMapping("/ger-all-service")
    public ResponseEntity<?> getAllTransactionService(){
        return trancsactionRequestService.getBankServices();
    }

    @PostMapping("/add-transaction-customer")
    public ResponseEntity<?> addNewCustomer(@RequestBody @Valid TransactionCustomer transactionCustomer){
        return trancsactionRequestService.addCustomer(transactionCustomer);
    }

    @PutMapping("/update-transaction-customer/{recordId}")
    public ResponseEntity<?> updateTransactionCustomerDetails(@RequestBody @Valid TransactionCustomer transactionCustomer
            ,@PathVariable int recordId){
        return trancsactionRequestService.updateTransactionCustomerDetails(transactionCustomer,recordId);
    }

    @GetMapping("/get-all-transaction-customers")
    public ResponseEntity<?> getAllCusmtomers(){
        return trancsactionRequestService.getAllTransactionCustomerDetails();
    }

    @GetMapping("/get-one-transaction-customer/{recordId}")
    public ResponseEntity<?> getOneCustomer(@PathVariable int recordId){
        return trancsactionRequestService.getOneCustomerDetails(recordId);
    }

    @GetMapping("/get-customer-from-identity/{identity}")
    public ResponseEntity<?> getCustomerFromIdentity(@PathVariable String identity){
        return trancsactionRequestService.getCustomerDetailsFormIndentity(identity);
    }

    @GetMapping("/get-customer-from-identity")
    public ResponseEntity<?> getCustomerFromIdentityFilterByDate(@RequestParam(name = "identification")
                                                              String identification, @RequestParam("date") String date){
        return trancsactionRequestService.getCustomerDetailsOfAIdentityFilterByDate(identification,date);
    }

    @GetMapping("/get-trasanction-customer-records-filter-by-date")
    public ResponseEntity<?> getTransactionRecordsFilterByDate(@RequestParam("date") String date){
        return trancsactionRequestService.getTransactionCustomerDetailsFilterByDate(date);
    }

    @PostMapping("/add-customer-to-trasaction-request")
    public ResponseEntity<?> addCustomerToATransactionRequest(@RequestBody CustomerTransactionDTO customerTransactionDTO){
        return trancsactionRequestService.addCustomerToATransactionRequest(customerTransactionDTO.getCustomerId(),
                                                                      customerTransactionDTO.getTransactionRequestId());
    }

    @GetMapping("/get-all-customer-transaction-requests")
    public ResponseEntity<?> getAllTransactionRequests(@RequestParam("customerRequestId") int customerID){
        return trancsactionRequestService.getAllCustomerTransactionRequests(customerID);
    }

    @GetMapping("/get-all-customer-transaction-requests-filter-by-date")
    public ResponseEntity<?> getAllCustomerTransactionRequestsFilterByDate(@RequestParam("customerRequestId") int customerId,@RequestParam("date") String date){
        return trancsactionRequestService.getAllCustomerTransactionRequestsFilterByDate(customerId,date);
    }

    @GetMapping("/get-form-of-a-transactionRequest")
    public ResponseEntity<?> getTransactionRequestView(@RequestParam("customerRequestId") int customerId){
        return trancsactionRequestService.viewTransactionRequest(customerId);
    }

}
