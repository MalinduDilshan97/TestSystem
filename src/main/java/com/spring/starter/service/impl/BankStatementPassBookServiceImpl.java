package com.spring.starter.service.impl;

import com.spring.starter.DTO.BankStatementAccountDTO;
import com.spring.starter.Exception.CustomException;
import com.spring.starter.Repository.BankStatementAccountNoRepository;
import com.spring.starter.Repository.CustomerServiceRequestRepository;
import com.spring.starter.Repository.EstatementFacilityRepository;
import com.spring.starter.Repository.StatementFrequencyRepository;
import com.spring.starter.model.*;
import com.spring.starter.service.BankStatementPassBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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

    @Override
    public ResponseEntity<?> estatementService(BankStatementAccountDTO bankStatementAccountDTO, int customerServiceRequistId) {

        ResponseModel responsemodel = new ResponseModel();
        Optional<CustomerServiceRequest> customerServiceRequest = customerServiceRequestRepository.findById(customerServiceRequistId);
        if(!customerServiceRequest.isPresent()) {
            responsemodel.setMessage("There is No such service Available");
            responsemodel.setStatus(false);
            return new ResponseEntity<>(responsemodel, HttpStatus.NO_CONTENT);
        }
        int serviceRequestId = customerServiceRequest.get().getServiceRequest().getDigiFormId();
        if(serviceRequestId != 11)
        {
            responsemodel.setMessage("Invalied Request");
            responsemodel.setStatus(false);
            return new ResponseEntity<>(responsemodel, HttpStatus.BAD_REQUEST);
        }
        EstatementFacility estatementFacility = new EstatementFacility();
        Optional<EstatementFacility> estatementFacilityOpt = estatementFacilityRepository.getFormFromCSR(customerServiceRequistId);
        List<BankStatementAccountNo> bankStatementAccountNolist = new ArrayList<>();
        if(estatementFacilityOpt.isPresent()){
            estatementFacility.setEstatementFacilityID(estatementFacilityOpt.get().getEstatementFacilityID());
            List<BankStatementAccountNo> bankStatementAccountNos = new ArrayList<>();
            for(BankStatementAccountNo accountNo : bankStatementAccountNos){
                bankStatementAccountNoRepository.delete(accountNo);
            }
        }

        for(String s : bankStatementAccountDTO.getBankStatementAccountNo()){
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
        try{
            estatementFacilityRepository.save(estatementFacility);
            responsemodel.setMessage("Service Saved Successfully");
            responsemodel.setStatus(true);
            return new ResponseEntity<>(responsemodel, HttpStatus.CREATED);
        } catch (Exception e){
            throw new CustomException(e.getMessage());
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
        if(serviceRequestId != 12)
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


}
