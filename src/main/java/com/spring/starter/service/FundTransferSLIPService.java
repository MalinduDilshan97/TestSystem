package com.spring.starter.service;

import com.spring.starter.model.FundTransferSLIPS;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface FundTransferSLIPService {

    public ResponseEntity<?> addNewFundTransferSlipRequest(FundTransferSLIPS fundTransferSLIPS,int customerTransactionRequest) throws Exception;

    public ResponseEntity<?> getFundTransferSlipRequest(int fundTransferSlipID);

    public ResponseEntity<?> addSignatureForSLIP(MultipartFile multipartFile, int customerTransactionRequestId);

    public ResponseEntity<?> addFileToSLIP(MultipartFile file , String fileType, int customerServiceRequestId);
}
