package com.spring.starter.service;

import com.spring.starter.model.FundTransferCEFT;
import org.springframework.http.ResponseEntity;

public interface FundTransferCEFTService {

    public ResponseEntity<?> addNewFundTransferCEFTRequest(FundTransferCEFT fundTransferSLIPS, int customerTransactionRequest) throws Exception;

    public ResponseEntity<?> getFundTransferCEFTRequest(int fundTransferSlipID);

}
