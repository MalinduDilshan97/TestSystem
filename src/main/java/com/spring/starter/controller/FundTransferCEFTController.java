package com.spring.starter.controller;

import com.spring.starter.model.FundTransferCEFT;
import com.spring.starter.service.FundTransferCEFTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("Transaction-Request/fundtrasnfet-other-ceft")
public class FundTransferCEFTController {

    @Autowired
    FundTransferCEFTService fundTransferCEFTService;

    @PostMapping
    public ResponseEntity<?> addNewOtherBankCEFT(@RequestBody @Valid FundTransferCEFT fundTransferCEFT , @RequestParam(name="requestId") int requestId) throws Exception {
        return fundTransferCEFTService.addNewFundTransferCEFTRequest(fundTransferCEFT,requestId);
    }

    @PutMapping
    public ResponseEntity<?> updateNewOtherBankCEFT(@RequestBody @Valid FundTransferCEFT fundTransferCEFT , @RequestParam(name="requestId") int requestId) throws Exception {
        return fundTransferCEFTService.addNewFundTransferCEFTRequest(fundTransferCEFT,requestId);
    }

    @GetMapping("/{OtherbankServiceCEFTId}")
    public ResponseEntity<?> getOtherbankServiceCEFT(@PathVariable int OtherbankServiceCEFTId){
        return fundTransferCEFTService.getFundTransferCEFTRequest(OtherbankServiceCEFTId);
    }

    @GetMapping("/testing")
    public ResponseEntity<?> testing(){
        FundTransferCEFT fundTransferCEFT = new FundTransferCEFT();
        fundTransferCEFT.setAccountName("lakith muthugala");
        fundTransferCEFT.setAmmount(10000.0);
        fundTransferCEFT.setCreditingAccountBank("sampath");
        fundTransferCEFT.setBranch("horana");
        fundTransferCEFT.setReason("there is no reason");

        return new ResponseEntity<>(fundTransferCEFT,HttpStatus.OK);
    }
}
