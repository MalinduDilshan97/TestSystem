package com.spring.starter.service;

import com.spring.starter.DTO.CashWithdrawalDTO;
import com.spring.starter.DTO.FileDTO;
import com.spring.starter.DTO.DetailsUpdateDTO;
import com.spring.starter.DTO.TransactionSignatureDTO;
import org.springframework.http.ResponseEntity;

public interface CashWithdrawalService {

    public ResponseEntity<?> cashWithdrawal(CashWithdrawalDTO cashWithdrawalDTO,int customerTransactionRequestId);

    public ResponseEntity<?> saveTrasnsactionSignature(TransactionSignatureDTO signatureDTO) throws Exception;

    public ResponseEntity<?> uploadFilesToCashWithdrawls(FileDTO fileDTO) throws Exception;

    public ResponseEntity<?> updateCashWithdrawal (CashWithdrawalDTO cashWithdrawalDTO,
                   int customerTransactionRequestId, DetailsUpdateDTO detailsUpdateDTO) throws Exception;

    public ResponseEntity<?> test ();

    }
