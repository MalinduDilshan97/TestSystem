package com.spring.starter.controller;

import com.spring.starter.model.FundTransferCEFT;
import com.spring.starter.model.FundTransferSLIPS;
import com.spring.starter.service.FundTransferCEFTService;
import com.spring.starter.service.FundTransferSLIPService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("Transaction-Request/fundtrasnfet-other-slip")
public class FundTransferSLIPController {

    @Autowired
    FundTransferSLIPService fundTransferSLIPService;

    @PostMapping
    public ResponseEntity<?> addNewOtherBankSLIP(@RequestBody @Valid FundTransferSLIPS fundTransferSLIP , @RequestParam(name="requestId") int requestId) throws Exception {
        return fundTransferSLIPService.addNewFundTransferSlipRequest(fundTransferSLIP,requestId);
    }

    @PutMapping
    public ResponseEntity<?> updateNewOtherBankSLIP(@RequestBody @Valid FundTransferSLIPS fundTransferSLIPS, @RequestParam(name="requestId") int requestId) throws Exception {
        return fundTransferSLIPService.addNewFundTransferSlipRequest(fundTransferSLIPS,requestId);
    }

    @GetMapping("/{OtherbankServiceCEFTId}")
    public ResponseEntity<?> getOtherbankServiceSLIP(@PathVariable int OtherbankServiceCEFTId){
        return fundTransferSLIPService.getFundTransferSlipRequest(OtherbankServiceCEFTId);
    }

}
