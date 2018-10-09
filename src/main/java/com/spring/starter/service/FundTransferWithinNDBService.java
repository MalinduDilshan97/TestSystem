package com.spring.starter.service;

import com.spring.starter.DTO.FundTransferWithinNDBDTO;
import org.springframework.http.ResponseEntity;

public interface FundTransferWithinNDBService {

    public ResponseEntity<?> saveFundTransferWithinNDBRequest(FundTransferWithinNDBDTO transferWithinNDBDTO, int customerTransactionRequestId);

}
