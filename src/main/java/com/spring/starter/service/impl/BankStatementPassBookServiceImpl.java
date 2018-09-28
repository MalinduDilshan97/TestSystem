package com.spring.starter.service.impl;

import com.spring.starter.DTO.AccountStatementIssueRequestDTO;
import com.spring.starter.DTO.DuplicatePassBookRequestDTO;
import com.spring.starter.Exception.CustomException;
import com.spring.starter.Repository.AccountStatementIssueRequestRepository;
import com.spring.starter.Repository.CustomerServiceRequestRepository;
import com.spring.starter.Repository.DuplicatePassBookRequestRepository;
import com.spring.starter.model.AccountStatementIssueRequest;
import com.spring.starter.model.CustomerServiceRequest;
import com.spring.starter.model.DuplicatePassBookRequest;
import com.spring.starter.model.ResponseModel;
import com.spring.starter.service.BankStatementPassBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;


@Service
@Transactional
public class BankStatementPassBookServiceImpl implements BankStatementPassBookService {

    @Autowired
    private DuplicatePassBookRequestRepository duplicatePassBookRequestRepository;
    @Autowired
    private CustomerServiceRequestRepository customerServiceRequestRepository;
    @Autowired
    private AccountStatementIssueRequestRepository accountStatementIssueRequestRepository;

    private ResponseModel res = new ResponseModel();
    DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    public ResponseEntity<?> duplicatePassBookRequest(DuplicatePassBookRequestDTO duplicatePassBookRequestDTO) {
        Optional<CustomerServiceRequest> optional = customerServiceRequestRepository.findById(duplicatePassBookRequestDTO.getCustomerServiceRequestId());
        if (!optional.isPresent()) {
            res.setMessage(" No Data Found To Complete The Request");
            res.setStatus(false);
            return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
        } else {

            CustomerServiceRequest customerServiceRequest = optional.get();
            DuplicatePassBookRequest duplicatePassBookRequest = new DuplicatePassBookRequest();

            int serviceRequestId = customerServiceRequest.getServiceRequest().getDigiFormId();
            if (serviceRequestId != 3) {
                res.setMessage("Invalid Request");
                res.setStatus(false);
                return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
            }
            Optional<DuplicatePassBookRequest> bookRequest = duplicatePassBookRequestRepository.getFormFromCSR(serviceRequestId);
            if (bookRequest.isPresent()) {
                duplicatePassBookRequest.setDuplicatePassBookRequestId(bookRequest.get().getDuplicatePassBookRequestId());
            }

            duplicatePassBookRequest.setAccountNumber(duplicatePassBookRequestDTO.getAccountNumber());
            duplicatePassBookRequest.setCustomerServiceRequest(customerServiceRequest);

            if (duplicatePassBookRequestRepository.save(duplicatePassBookRequest) != null) {
                res.setMessage(" Request Successfully Saved To The System");
                res.setStatus(true);
                return new ResponseEntity<>(res, HttpStatus.CREATED);
            } else {
                res.setMessage(" Failed TO Save The Request... Operation Unsuccessful");
                res.setStatus(false);
                return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
            }
        }
    }

    @Override
    public ResponseEntity<?> AccountStatement(AccountStatementIssueRequestDTO accountStatementIssueRequestDTO) {
        Optional<CustomerServiceRequest> optional = customerServiceRequestRepository.findById(accountStatementIssueRequestDTO.getCustomerServiceRequestId());
        if (!optional.isPresent()) {
            res.setMessage(" No Data Found To Complete The Request");
            res.setStatus(false);
            return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
        } else {

            CustomerServiceRequest customerServiceRequest = optional.get();
            AccountStatementIssueRequest accountStatementIssueRequest = new AccountStatementIssueRequest();


            try {
                Date from = df.parse(accountStatementIssueRequestDTO.getFromDate());
                Date to = df.parse(accountStatementIssueRequestDTO.getToDate());

                int serviceRequestId = customerServiceRequest.getServiceRequest().getDigiFormId();
                if (serviceRequestId != 3) {
                    res.setMessage("Invalid Request");
                    res.setStatus(false);
                    return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
                }

                Optional<AccountStatementIssueRequest>request=accountStatementIssueRequestRepository.getFormFromCSR(serviceRequestId);
                if (request.isPresent()){
                    accountStatementIssueRequest.setAccountStatementIssueRequestId(request.get().getAccountStatementIssueRequestId());
                }

                accountStatementIssueRequest.setAccountNo(accountStatementIssueRequestDTO.getAccountNo());
                accountStatementIssueRequest.setFromDate(from);
                accountStatementIssueRequest.setToDate(to);
                accountStatementIssueRequest.setNatureOfStatement(accountStatementIssueRequestDTO.getNatureOfStatement());
                accountStatementIssueRequest.setCustomerServiceRequest(customerServiceRequest);

                if (accountStatementIssueRequestRepository.save(accountStatementIssueRequest) != null) {
                    res.setMessage(" Request Successfully Saved To The System");
                    res.setStatus(true);
                    return new ResponseEntity<>(res, HttpStatus.CREATED);
                } else {
                    res.setMessage(" Failed TO Save The Request... Operation Unsuccessful");
                    res.setStatus(false);
                    return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
                }


            } catch (ParseException e) {
                throw new CustomException("Failed TO Save The Request... Operation Unsuccessful Input Date To This Format (YYYY-MM-DD)");
            }


        }

    }
}
