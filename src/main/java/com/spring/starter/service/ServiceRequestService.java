package com.spring.starter.service;

import java.security.Principal;
import java.util.Optional;

import com.spring.starter.DTO.SignatureDTO;
import org.springframework.http.ResponseEntity;

import com.spring.starter.DTO.CustomerDTO;
import com.spring.starter.model.Customer;
import com.spring.starter.model.ServiceRequest;

public interface ServiceRequestService {

    public ResponseEntity<?> addNewServiceRequest(ServiceRequest serviceRequest);

    public ResponseEntity<?> getBankServices();

    public ResponseEntity<?> addNewCustomer(CustomerDTO customerDTO);

    public ResponseEntity<?> addAServiceToACustomer(int customerId, int serviceRequestId);

    public Optional<Customer> getCustomer();

    public ResponseEntity<?> getAllServiceRequests(int customerId, String date);

    public ResponseEntity<?> getAllCustomerRequests(int customerId);

    public ResponseEntity<?> getCustomerDetailsWithServiceRequests(int customerId);

    public ResponseEntity<?> completeARequest(Principal principal, int requestId);

    public ResponseEntity<?> addAStaffHandled(Principal principal, int requestId);

    public ResponseEntity<?> completeAllCustomerRequests(Principal principal, int customerId);

    public ResponseEntity<?> getServiceRequestForm(int customerServiceRequestId);

    public ResponseEntity<?> saveSignature(SignatureDTO signatureDTO);

}
