package com.spring.starter.service;

import com.spring.starter.DTO.CashWithdrawalDTO;
import com.spring.starter.DTO.CashWithdrawalFileDTO;
import com.spring.starter.DTO.CashWithdrawalUpdateDTO;
import com.spring.starter.DTO.TransactionSignatureDTO;
import org.springframework.http.ResponseEntity;

public interface CashWithdrawalService {

    public ResponseEntity<?> cashWithdrawal(CashWithdrawalDTO cashWithdrawalDTO,int customerTransactionRequestId);

    public ResponseEntity<?> saveTrasnsactionSignature(TransactionSignatureDTO signatureDTO) throws Exception;

    public ResponseEntity<?> uploadFilesToCashWithdrawls(CashWithdrawalFileDTO cashWithdrawalFileDTO) throws Exception;

    public ResponseEntity<?> updateCashWithdrawal (CashWithdrawalDTO cashWithdrawalDTO,
                   int customerTransactionRequestId, CashWithdrawalUpdateDTO cashWithdrawalUpdateDTO) throws Exception;

    public ResponseEntity<?> test ();

    }
