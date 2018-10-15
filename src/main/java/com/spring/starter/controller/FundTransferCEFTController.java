package com.spring.starter.controller;

import com.spring.starter.model.Bank;
import com.spring.starter.model.Branch;
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

        Bank bank = new Bank();
        bank.setMx_bank_code(7011);

        Branch branch = new Branch();
        branch.setBranch_id(1);

        FundTransferCEFT fundTransferCEFT = new FundTransferCEFT();
        fundTransferCEFT.setAccountName("lakith muthugala");
        fundTransferCEFT.setAmmount(10000.0);
        fundTransferCEFT.setBank(bank);
        fundTransferCEFT.setBranch(branch);
        fundTransferCEFT.setReason("there is no reason");

        return new ResponseEntity<>(fundTransferCEFT,HttpStatus.OK);
    }
}
