package com.spring.starter.service;

import java.security.Principal;
import java.util.Date;
import java.util.Optional;

import com.spring.starter.DTO.SignatureDTO;
import org.springframework.http.ResponseEntity;

import com.spring.starter.DTO.CustomerDTO;
import com.spring.starter.model.Customer;
import com.spring.starter.model.ServiceRequest;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

public interface ServiceRequestService {

    public ResponseEntity<?> addNewServiceRequest(ServiceRequest serviceRequest);

    public ResponseEntity<?> getBankServices();

    public ResponseEntity<?> addNewCustomer(CustomerDTO customerDTO,HttpServletRequest request);

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

    public ResponseEntity<?> saveTif(MultipartFile file,
                                            int serviceRequestCustomerId,
                                            int queueId) throws Exception;

    public ResponseEntity<?> getTifs(String date) throws Exception;

    public ResponseEntity<?> getFIleTypes(int customerRequestId);

    public ResponseEntity<?> getCustomerDetailsByDate(String date);

}
