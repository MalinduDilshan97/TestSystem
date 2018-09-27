package com.spring.starter.service;

import com.spring.starter.DTO.WithholdingFdCdDTO;
import com.spring.starter.model.OtherFdCdRelatedRequest;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface FdorCdService {


    ResponseEntity<?> addWithHoldingTaxDC(WithholdingFdCdDTO fdCdNumbers, int requestId);

    ResponseEntity<?> addRelatedRequest(OtherFdCdRelatedRequest otherFdCdRelatedRequest, int requestId);
}
