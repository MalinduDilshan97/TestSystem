package com.spring.starter.service;

import com.spring.starter.model.FundTransferSLIPS;
import org.springframework.http.ResponseEntity;

public interface FundTransferSLIPService {

    public ResponseEntity<?> addNewFundTransferSlipRequest(FundTransferSLIPS fundTransferSLIPS,int customerTransactionRequest) throws Exception;

    public ResponseEntity<?> getFundTransferSlipRequest(int fundTransferSlipID);
}
