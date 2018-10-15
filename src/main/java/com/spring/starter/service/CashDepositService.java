package com.spring.starter.service;

import com.spring.starter.DTO.CashWithdrawalFileDTO;
import com.spring.starter.DTO.CashWithdrawalUpdateDTO;
import com.spring.starter.DTO.TransactionSignatureDTO;
import com.spring.starter.model.CashDeposit;
import org.springframework.http.ResponseEntity;

public interface CashDepositService {

    public ResponseEntity<?> saveNewCashDepositRequest(CashDeposit cashDeposit , int customerTransactionRequestId);

    public ResponseEntity<?> uploadFilesToCashDeposit(CashWithdrawalFileDTO cashWithdrawalFileDTO) throws Exception;

    public ResponseEntity<?> updateCashDeposit (CashDeposit cashDeposit,
                                                   int customerTransactionRequestId, CashWithdrawalUpdateDTO cashWithdrawalUpdateDTO) throws Exception;

    public ResponseEntity<?> saveTrasnsactionSignature(TransactionSignatureDTO signatureDTO) throws Exception;

    public ResponseEntity<?> getCashDepositRequest(int cashDepositId);


}
