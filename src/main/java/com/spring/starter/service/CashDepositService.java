package com.spring.starter.service;

import com.spring.starter.model.CashDeposit;
import org.springframework.http.ResponseEntity;

public interface CashDepositService {

    public ResponseEntity<?> saveNewCashDepositRequest(CashDeposit cashDeposit , int customerTransactionRequestId);

    public ResponseEntity<?> getCashDepositRequest(int cashDepositId);


}
