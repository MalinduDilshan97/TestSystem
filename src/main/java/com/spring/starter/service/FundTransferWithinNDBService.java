package com.spring.starter.service;

import com.spring.starter.DTO.DetailsUpdateDTO;
import com.spring.starter.DTO.FileDTO;
import com.spring.starter.DTO.FundTransferWithinNDBDTO;
import com.spring.starter.DTO.TransactionSignatureDTO;
import org.springframework.http.ResponseEntity;

public interface FundTransferWithinNDBService {

    public ResponseEntity<?> saveFundTransferWithinNDBRequest(FundTransferWithinNDBDTO transferWithinNDBDTO, int customerTransactionRequestId);

    public ResponseEntity<?> uploadFilesToFundTransferWithinNDBRequest(FileDTO fileDTO) throws Exception;

    public ResponseEntity<?> updateFundTransferWithinNDBRequest (FundTransferWithinNDBDTO fundTransferWithinNDBDTO,
                                                int customerTransactionRequestId, DetailsUpdateDTO detailsUpdateDTO) throws Exception;

    public ResponseEntity<?> saveTrasnsactionSignature(TransactionSignatureDTO signatureDTO) throws Exception;

}
