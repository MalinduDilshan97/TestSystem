package com.spring.starter.service.impl;

import com.spring.starter.DTO.AccountStatementIssueRequestDTO;
import com.spring.starter.DTO.BankStatementAccountDTO;
import com.spring.starter.DTO.DuplicatePassBookRequestDTO;
import com.spring.starter.Exception.CustomException;
import com.spring.starter.Repository.*;
import com.spring.starter.configuration.ServiceRequestIdConfig;
import com.spring.starter.model.*;
import com.spring.starter.service.BankStatementPassBookService;
import com.spring.starter.service.ServiceRequestCustomerLogService;
import com.spring.starter.service.ServiceRequestFormLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;


@Service
@Transactional
public class BankStatementPassBookServiceImpl implements BankStatementPassBookService {

    @Autowired
    private CustomerServiceRequestRepository customerServiceRequestRepository;
    @Autowired
    EstatementFacilityRepository estatementFacilityRepository;
    @Autowired
    BankStatementAccountNoRepository bankStatementAccountNoRepository;
    @Autowired
    StatementFrequencyRepository statementFrequencyRepository;
    @Autowired
    private DuplicatePassBookRequestRepository duplicatePassBookRequestRepository;
    @Autowired
    private AccountStatementIssueRequestRepository accountStatementIssueRequestRepository;
    @Autowired
    private ServiceRequestCustomerLogService serviceRequestCustomerLogService;
    @Autowired
    private ServiceRequestFormLogService serviceRequestFormLogService;

    private ResponseModel res = new ResponseModel();
    DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    private ServiceRequestCustomerLog serviceRequestCustomerLog = new ServiceRequestCustomerLog();
    private ServiceRequestFormLog serviceRequestFormLog = new ServiceRequestFormLog();

    @Override
    public ResponseEntity<?> estatementService(BankStatementAccountDTO bankStatementAccountDTO, int customerServiceRequistId) {

        ResponseModel responsemodel = new ResponseModel();
        Optional<CustomerServiceRequest> customerServiceRequest = customerServiceRequestRepository.findById(customerServiceRequistId);
        if (!customerServiceRequest.isPresent()) {
            responsemodel.setMessage("There is No such service Available");
            responsemodel.setStatus(false);
            return new ResponseEntity<>(responsemodel, HttpStatus.NO_CONTENT);
        }
        int serviceRequestId = customerServiceRequest.get().getServiceRequest().getDigiFormId();
        if (serviceRequestId != ServiceRequestIdConfig.PI_ACTIVE_CACEL_ESTATEMENT_FACILITY_FOR_ACCOUNTS) {
            responsemodel.setMessage("Invalid Request");
            responsemodel.setStatus(false);
            return new ResponseEntity<>(responsemodel, HttpStatus.BAD_REQUEST);
        }
        EstatementFacility estatementFacility = new EstatementFacility();
        Optional<EstatementFacility> estatementFacilityOpt = estatementFacilityRepository.getFormFromCSR(customerServiceRequistId);
        List<BankStatementAccountNo> bankStatementAccountNolist = new ArrayList<>();
        if (estatementFacilityOpt.isPresent()) {
            estatementFacility.setEstatementFacilityID(estatementFacilityOpt.get().getEstatementFacilityID());
            List<BankStatementAccountNo> bankStatementAccountNos = estatementFacilityOpt.get().getBankStatementAccountNo();
            for (BankStatementAccountNo accountNo : bankStatementAccountNos) {
                bankStatementAccountNoRepository.delete(accountNo);
            }
        }

        for (String s : bankStatementAccountDTO.getBankStatementAccountNo()) {
            BankStatementAccountNo no = new BankStatementAccountNo();
            no.setAccountNo(s);
            no = bankStatementAccountNoRepository.save(no);
            bankStatementAccountNolist.add(no);
        }

        estatementFacility.setActivateEstatement(bankStatementAccountDTO.isActivateEstatement());
        estatementFacility.setCancelEstatement(bankStatementAccountDTO.isActivateEstatement());
        estatementFacility.setBankStatementAccountNo(bankStatementAccountNolist);
        estatementFacility.setCustomerServiceRequest(customerServiceRequest.get());
        estatementFacility.setEmail(bankStatementAccountDTO.getEmail());
        try {
            estatementFacilityRepository.save(estatementFacility);
            responsemodel.setMessage("Service Saved Successfully");
            responsemodel.setStatus(true);
            return new ResponseEntity<>(responsemodel, HttpStatus.CREATED);
        } catch (Exception e) {
            throw new CustomException(e.getMessage());

        }
    }

    @Override
    public ResponseEntity<?> duplicatePassBookRequest(DuplicatePassBookRequestDTO duplicatePassBookRequestDTO, HttpServletRequest request) {
        Optional<CustomerServiceRequest> optional = customerServiceRequestRepository.findById(duplicatePassBookRequestDTO.getCustomerServiceRequestId());
        if (!optional.isPresent()) {
            res.setMessage(" No Data Found To Complete The Request");
            res.setStatus(false);
            return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
        } else {

            CustomerServiceRequest customerServiceRequest = optional.get();
            DuplicatePassBookRequest duplicatePassBookRequest = new DuplicatePassBookRequest();

            int serviceRequestId = customerServiceRequest.getServiceRequest().getDigiFormId();
            if (serviceRequestId != ServiceRequestIdConfig.PASSBOOK_DUPLICATE_PASSBOOK_REQUEST) {
                res.setMessage("Invalid Request");
                res.setStatus(false);
                return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
            }
            Optional<DuplicatePassBookRequest> bookRequest = duplicatePassBookRequestRepository.getFormFromCSR(duplicatePassBookRequestDTO.getCustomerServiceRequestId());
            if (bookRequest.isPresent()) {
                duplicatePassBookRequest.setDuplicatePassBookRequestId(bookRequest.get().getDuplicatePassBookRequestId());
            }

            duplicatePassBookRequest.setCustomerServiceRequest(customerServiceRequest);

            if (duplicatePassBookRequestRepository.save(duplicatePassBookRequest) != null) {
                serviceRequestCustomerLog.setDate(java.util.Calendar.getInstance().getTime());
                serviceRequestCustomerLog.setIdentification(customerServiceRequest.getCustomer().getIdentification());
                serviceRequestCustomerLog.setIp(request.getRemoteAddr());
                serviceRequestCustomerLog.setMessage("Request Form Successfully Saved To The System");
                boolean result = serviceRequestCustomerLogService.saveServiceRequestCustomerLog(serviceRequestCustomerLog);

                if (result) {
                    serviceRequestFormLog.setDigiFormId(customerServiceRequest.getServiceRequest().getDigiFormId());
                    serviceRequestFormLog.setCustomerId(customerServiceRequest.getCustomer().getCustomerId());
                    serviceRequestFormLog.setDate(java.util.Calendar.getInstance().getTime());
                    serviceRequestFormLog.setFromId(customerServiceRequest.getServiceRequest().getServiceRequestId());
                    serviceRequestFormLog.setIp(request.getRemoteAddr());
                    serviceRequestFormLog.setStatus(true);
                    serviceRequestFormLog.setMessage("Request Form  Successfully Saved To The  System");
                    serviceRequestFormLogService.saveServiceRequestFormLog(serviceRequestFormLog);
                }

                res.setMessage(" Request Successfully Created In The System");
                res.setStatus(true);
                return new ResponseEntity<>(res, HttpStatus.CREATED);
            } else {
                serviceRequestCustomerLog.setDate(java.util.Calendar.getInstance().getTime());
                serviceRequestCustomerLog.setIdentification(customerServiceRequest.getCustomer().getIdentification());
                serviceRequestCustomerLog.setIp(request.getRemoteAddr());
                serviceRequestCustomerLog.setMessage("Failed TO Save The Request...  Operation Unsuccessful");
                boolean result = serviceRequestCustomerLogService.saveServiceRequestCustomerLog(serviceRequestCustomerLog);

                if (result) {
                    serviceRequestFormLog.setDigiFormId(customerServiceRequest.getServiceRequest().getDigiFormId());
                    serviceRequestFormLog.setCustomerId(customerServiceRequest.getCustomer().getCustomerId());
                    serviceRequestFormLog.setDate(java.util.Calendar.getInstance().getTime());
                    serviceRequestFormLog.setFromId(customerServiceRequest.getServiceRequest().getServiceRequestId());
                    serviceRequestFormLog.setIp(request.getRemoteAddr());
                    serviceRequestFormLog.setStatus(true);
                    serviceRequestFormLog.setMessage(" Failed TO Save The  Request... Operation  Unsuccessful");
                    serviceRequestFormLogService.saveServiceRequestFormLog(serviceRequestFormLog);
                }

                res.setMessage(" Failed TO Save The Request... Operation Unsuccessful");
                res.setStatus(false);
                return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
            }
        }
    }

    @Override
    public ResponseEntity<?> statementFrequencyService(StatementFrequency statementFrequency, int customerServiceRequistId) {
        ResponseModel responsemodel = new ResponseModel();
        Optional<CustomerServiceRequest> customerServiceRequest = customerServiceRequestRepository.findById(customerServiceRequistId);
        if(!customerServiceRequest.isPresent()) {
            responsemodel.setMessage("There is No such service Available");
            responsemodel.setStatus(false);
            return new ResponseEntity<>(responsemodel, HttpStatus.NO_CONTENT);
        }
        int serviceRequestId = customerServiceRequest.get().getServiceRequest().getDigiFormId();
        System.out.println(serviceRequestId);
        if(serviceRequestId != ServiceRequestIdConfig.CHANGE_STATEMENT_FREQUENCY_TO)
        {
            responsemodel.setMessage("Invalied Request");
            responsemodel.setStatus(false);
            return new ResponseEntity<>(responsemodel, HttpStatus.BAD_REQUEST);
        }
        Optional<StatementFrequency> frequency = statementFrequencyRepository.getFormFromCSR(customerServiceRequistId);
        if(frequency.isPresent()){
            statementFrequency.setStatementFrequency(frequency.get().getStatementFrequency());
        }

        if(statementFrequency.isAnnually() && statementFrequency.isBiAnnaully() && statementFrequency.isDaily() && statementFrequency.isMonthly() && statementFrequency.isQuarterly()){
            responsemodel.setMessage("Invalied Request");
            responsemodel.setStatus(false);
            return new ResponseEntity<>(responsemodel, HttpStatus.BAD_REQUEST);
        } else if(!statementFrequency.isAnnually() && !statementFrequency.isBiAnnaully() && !statementFrequency.isDaily() && !statementFrequency.isMonthly() && !statementFrequency.isQuarterly()){
            responsemodel.setMessage("Invalied Request");
            responsemodel.setStatus(false);
            return new ResponseEntity<>(responsemodel, HttpStatus.BAD_REQUEST);
        }

        try{
            statementFrequency.setCustomerServiceRequest(customerServiceRequest.get());
            statementFrequencyRepository.save(statementFrequency);
            responsemodel.setMessage("Service Created Successfully");
            responsemodel.setStatus(true);
            return new ResponseEntity<>(responsemodel, HttpStatus.CREATED);
        } catch (Exception e){
            throw new CustomException(e.getMessage());
        }

    }


    public ResponseEntity<?> AccountStatement(AccountStatementIssueRequestDTO accountStatementIssueRequestDTO, HttpServletRequest request) {
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
                if (serviceRequestId != ServiceRequestIdConfig.ISSUE_ACCAUNT_STATEMENT_FOR_PERIOD) {
                    res.setMessage("Invalid Request");
                    res.setStatus(false);
                    return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
                }

                Optional<AccountStatementIssueRequest> issueRequest = accountStatementIssueRequestRepository
                        .getFormFromCSR(accountStatementIssueRequestDTO.getCustomerServiceRequestId());
                if (issueRequest.isPresent()) {
                    accountStatementIssueRequest.setAccountStatementIssueRequestId(issueRequest.get().getAccountStatementIssueRequestId());
                }
                String statement;
                if(accountStatementIssueRequestDTO.getNatureOfStatement() == 1){
                    statement = "PRINTED";
                } else if (accountStatementIssueRequestDTO.getNatureOfStatement() == 2) {
                    statement = "E-STATEMENT";
                } else {
                    res.setMessage("Invalid statement type");
                    res.setStatus(false);
                    return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
                }

                accountStatementIssueRequest.setAccountNo(accountStatementIssueRequestDTO.getAccountNo());
                accountStatementIssueRequest.setFromDate(from);
                accountStatementIssueRequest.setToDate(to);
                accountStatementIssueRequest.setNatureOfStatement(statement);
                accountStatementIssueRequest.setCustomerServiceRequest(customerServiceRequest);

                if (accountStatementIssueRequestRepository.save(accountStatementIssueRequest) != null) {
                    res.setMessage(" Request Successfully Saved In The System");
                    res.setStatus(true);
                    return new ResponseEntity<>(res, HttpStatus.CREATED);


                } else {
                    serviceRequestCustomerLog.setDate(java.util.Calendar.getInstance().getTime());
                    serviceRequestCustomerLog.setIdentification(customerServiceRequest.getCustomer().getIdentification());
                    serviceRequestCustomerLog.setIp(request.getRemoteAddr());
                    serviceRequestCustomerLog.setMessage("Failed TO Save The  Request... Operation Unsuccessful");
                    boolean result = serviceRequestCustomerLogService.saveServiceRequestCustomerLog(serviceRequestCustomerLog);

                    if (result) {
                        serviceRequestFormLog.setDigiFormId(customerServiceRequest.getServiceRequest().getDigiFormId());
                        serviceRequestFormLog.setCustomerId(customerServiceRequest.getCustomer().getCustomerId());
                        serviceRequestFormLog.setDate(java.util.Calendar.getInstance().getTime());
                        serviceRequestFormLog.setFromId(customerServiceRequest.getServiceRequest().getServiceRequestId());
                        serviceRequestFormLog.setIp(request.getRemoteAddr());
                        serviceRequestFormLog.setStatus(true);
                        serviceRequestFormLog.setMessage(" Failed  TO Save The  Request... Operation Unsuccessful");
                        serviceRequestFormLogService.saveServiceRequestFormLog(serviceRequestFormLog);
                    }

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
