package com.spring.starter.service;

import com.spring.starter.DTO.CashWithdrawalDTO;
import org.springframework.http.ResponseEntity;

public interface CashWithdrawalService {

    public ResponseEntity<?> cashWithdrawal(CashWithdrawalDTO cashWithdrawalDTO,int customerTransactionRequestId);

}
