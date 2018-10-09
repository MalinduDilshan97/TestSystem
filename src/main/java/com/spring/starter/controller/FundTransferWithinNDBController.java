package com.spring.starter.controller;

import com.spring.starter.DTO.FundTransferWithinNDBDTO;
import com.spring.starter.service.FundTransferWithinNDBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("Transaction-Request/fund-transaction-within-ndb")
public class FundTransferWithinNDBController {

    @Autowired
    private FundTransferWithinNDBService fundTransferWithinNDBService;

    @PostMapping
    public ResponseEntity<?> saveFundTransferWithinNDBRequest(@Valid @RequestBody FundTransferWithinNDBDTO transferWithinNDBDTO, @RequestParam(name="requestId") int requestId){
        return fundTransferWithinNDBService.saveFundTransferWithinNDBRequest(transferWithinNDBDTO,requestId);
    }

    @PutMapping
    public ResponseEntity<?> updateFundTransferWithinNDBRequest(@Valid @RequestBody FundTransferWithinNDBDTO transferWithinNDBDTO,@RequestParam(name="requestId") int requestId){
        return fundTransferWithinNDBService.saveFundTransferWithinNDBRequest(transferWithinNDBDTO,requestId);
    }

}
