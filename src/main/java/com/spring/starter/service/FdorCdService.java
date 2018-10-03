package com.spring.starter.service;

import com.spring.starter.DTO.WithholdingFdCdDTO;
import com.spring.starter.model.DuplicateFdCdCert;
import com.spring.starter.model.OtherFdCdRelatedRequest;
import org.springframework.http.ResponseEntity;


public interface FdorCdService {


    ResponseEntity<?> addWithHoldingTaxDC(WithholdingFdCdDTO fdCdNumbers, int requestId);

    ResponseEntity<?> addRelatedRequest(OtherFdCdRelatedRequest otherFdCdRelatedRequest, int requestId);

    ResponseEntity<?> addDuplicateFdCdCert(DuplicateFdCdCert duplicateFdCdCert, int requestId);
}
