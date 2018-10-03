package com.spring.starter.controller;

import com.spring.starter.DTO.WithholdingFdCdDTO;
import com.spring.starter.model.DuplicateFdCdCert;
import com.spring.starter.model.OtherFdCdRelatedRequest;
import com.spring.starter.service.FdorCdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.xml.ws.Response;

@RestController
@RequestMapping("/serviceRequest")
public class FdorCdController {
    @Autowired
    FdorCdService fdorCdService;

    @PostMapping("addWithHoldingTaxDC")
    public ResponseEntity<?> addWithHoldingTaxDC(@RequestBody WithholdingFdCdDTO withholdingFdCd, @RequestParam(name = "requestId") int requestId) {
        return fdorCdService.addWithHoldingTaxDC(withholdingFdCd, requestId);
    }

    @PostMapping("addRelatedRequest")
    public ResponseEntity<?> addRelatedRequest(@RequestBody OtherFdCdRelatedRequest otherFdCdRelatedRequest, @RequestParam(name = "requestId") int requestId) {
        return fdorCdService.addRelatedRequest(otherFdCdRelatedRequest, requestId);
    }

    @PostMapping("addDuplicateFdCdCert")
    public ResponseEntity<?> addDuplicateFdCdCert(@RequestBody DuplicateFdCdCert duplicateFdCdCert, @RequestParam(name = "requestId") int requestId) {
        return fdorCdService.addDuplicateFdCdCert(duplicateFdCdCert, requestId);
    }
}
