package com.spring.starter.service.impl;

import com.spring.starter.DTO.FundTransferWithinNDBDTO;
import com.spring.starter.Repository.CustomerTransactionRequestRepository;
import com.spring.starter.Repository.FundTransferWithinNDBRepository;
import com.spring.starter.Repository.NDBBranchRepository;
import com.spring.starter.configuration.TransactionIdConfig;
import com.spring.starter.model.CustomerTransactionRequest;
import com.spring.starter.model.FundTransferWithinNDB;
import com.spring.starter.model.NDBBranch;
import com.spring.starter.model.ResponseModel;
import com.spring.starter.service.FundTransferWithinNDBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class FundTransferWithinNDBServiceImpl implements FundTransferWithinNDBService {

    @Autowired
    private FundTransferWithinNDBRepository fundTransferWithinNDBRepository;
    @Autowired
    private CustomerTransactionRequestRepository customerTransactionRequestRepository;
    @Autowired
    private NDBBranchRepository ndbBranchRepository;

    private ResponseModel responseModel = new ResponseModel();

    @Override
    public ResponseEntity<?> saveFundTransferWithinNDBRequest(FundTransferWithinNDBDTO fundTransferWithinNDBDTO, int customerTransactionRequestId) {
        try {

            FundTransferWithinNDB fundTransferWithinNDB= new FundTransferWithinNDB();

            Optional<CustomerTransactionRequest> customerTransactionRequest = customerTransactionRequestRepository.findById(customerTransactionRequestId);

            if(!customerTransactionRequest.isPresent()){
                responseModel.setMessage("Invalid Transaction Request");
                responseModel.setStatus(false);
                return new ResponseEntity<>(responseModel,HttpStatus.BAD_REQUEST);
            }

            Optional<NDBBranch> branchOptional= ndbBranchRepository.findById(fundTransferWithinNDBDTO.getNdbBranchId());

            if(!branchOptional.isPresent()){
                responseModel.setMessage("Invalid Bank Branch");
                responseModel.setStatus(false);
                return new ResponseEntity<>(responseModel,HttpStatus.BAD_REQUEST);
            }

            int id = customerTransactionRequest.get().getTransactionRequest().getDigiFormId();
            if(id != TransactionIdConfig.FUND_TRANSFER_WITHIN_NDB){
                responseModel.setMessage("Invalid Transaction Request Id");
                responseModel.setStatus(false);
                return new ResponseEntity<>(responseModel,HttpStatus.BAD_REQUEST);
            }

            Optional<FundTransferWithinNDB> optional = fundTransferWithinNDBRepository.getFormFromCSR(customerTransactionRequestId);
            if(optional.isPresent()){
                fundTransferWithinNDB.setFundTransferWithinNdbId(optional.get().getFundTransferWithinNdbId());
            }

            fundTransferWithinNDB.setCustomerTransactionRequest(customerTransactionRequest.get());
            fundTransferWithinNDB.setNdbBranch(branchOptional.get());
            fundTransferWithinNDB.setDate(fundTransferWithinNDBDTO.getDate());
            fundTransferWithinNDB.setFromAccount(fundTransferWithinNDBDTO.getFromAccount());
            fundTransferWithinNDB.setFromAccountType(fundTransferWithinNDBDTO.getFromAccountType());
            fundTransferWithinNDB.setCurrency(fundTransferWithinNDBDTO.getCurrency());
            fundTransferWithinNDB.setAmount(fundTransferWithinNDBDTO.getAmount());
            fundTransferWithinNDB.setToAccount(fundTransferWithinNDBDTO.getToAccount());

            FundTransferWithinNDB ndb=fundTransferWithinNDBRepository.save(fundTransferWithinNDB);
            if (ndb!=null){
                return new ResponseEntity<>(ndb,HttpStatus.CREATED);
            }else{
                responseModel.setMessage("Failed To Save");
                responseModel.setStatus(false);
                return new ResponseEntity<>(responseModel,HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            responseModel.setMessage("Failed To Save");
            responseModel.setStatus(false);
            return new ResponseEntity<>(responseModel,HttpStatus.BAD_REQUEST);
        }

    }
}
