package com.spring.starter.service.impl;

import com.spring.starter.DTO.CashWithdrawalDTO;
import com.spring.starter.Repository.CashWithdrawalRepository;
import com.spring.starter.Repository.CustomerTransactionRequestRepository;
import com.spring.starter.Repository.NDBBranchRepository;
import com.spring.starter.configuration.TransactionIdConfig;
import com.spring.starter.model.CashWithdrawal;
import com.spring.starter.model.CustomerTransactionRequest;
import com.spring.starter.model.NDBBranch;
import com.spring.starter.model.ResponseModel;
import com.spring.starter.service.CashWithdrawalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class CashWithdrawalServiceImpl implements CashWithdrawalService {

    @Autowired
    private CashWithdrawalRepository cashWithdrawalRepository;
    @Autowired
    private CustomerTransactionRequestRepository customerTransactionRequestRepository;
    @Autowired
    private NDBBranchRepository ndbBranchRepository;

    private ResponseModel responseModel = new ResponseModel();

    @Override
    public ResponseEntity<?> cashWithdrawal(CashWithdrawalDTO cashWithdrawalDTO, int customerTransactionRequestId) {

        try {
            CashWithdrawal cashWithdrawal = new CashWithdrawal();

            Optional<CustomerTransactionRequest> customerTransactionRequest = customerTransactionRequestRepository.findById(customerTransactionRequestId);

            if(!customerTransactionRequest.isPresent()){
                responseModel.setMessage("Invalid Transaction Request");
                responseModel.setStatus(false);
                return new ResponseEntity<>(responseModel,HttpStatus.BAD_REQUEST);
            }

            Optional<NDBBranch> branchOptional= ndbBranchRepository.findById(cashWithdrawalDTO.getNdbBranchId());

            if(!branchOptional.isPresent()){
                responseModel.setMessage("Invalid Bank Branch");
                responseModel.setStatus(false);
                return new ResponseEntity<>(responseModel,HttpStatus.BAD_REQUEST);
            }

            int id = customerTransactionRequest.get().getTransactionRequest().getDigiFormId();
            if(id != TransactionIdConfig.WITHDRAWALS){
                responseModel.setMessage("Invalid Transaction Request Id");
                responseModel.setStatus(false);
                return new ResponseEntity<>(responseModel,HttpStatus.BAD_REQUEST);
            }

            Optional<CashWithdrawal> optional=cashWithdrawalRepository.getFormFromCSR(customerTransactionRequestId);
            if (optional.isPresent()){
                cashWithdrawal.setCashWithdrawalId(optional.get().getCashWithdrawalId());
            }

            cashWithdrawal.setCustomerTransactionRequest(customerTransactionRequest.get());
            cashWithdrawal.setNdbBranch(branchOptional.get());
            cashWithdrawal.setDate(cashWithdrawalDTO.getDate());
            cashWithdrawal.setAccountNo(cashWithdrawalDTO.getAccountNo());
            cashWithdrawal.setAccountHolder(cashWithdrawalDTO.getAccountHolder());
            cashWithdrawal.setCurrency(cashWithdrawalDTO.getCurrency());
            cashWithdrawal.setAmount(cashWithdrawalDTO.getAmount());
            cashWithdrawal.setAmountInWords(cashWithdrawalDTO.getAmountInWords());
            cashWithdrawal.setIdentity(cashWithdrawalDTO.getIdentity());

            CashWithdrawal save=cashWithdrawalRepository.save(cashWithdrawal);
            if (save!=null){
                return new ResponseEntity<>(save,HttpStatus.CREATED);
            }else{
                responseModel.setMessage("Failed To Save ");
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
