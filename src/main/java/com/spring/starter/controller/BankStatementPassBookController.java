package com.spring.starter.controller;

import com.spring.starter.DTO.BankStatementAccountDTO;
import com.spring.starter.model.StatementFrequency;
import com.spring.starter.service.BankStatementPassBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/serviceRequest/bankStatementPassBook")
@CrossOrigin
public class BankStatementPassBookController {

    @Autowired
    BankStatementPassBookService bankStatementPassBookService;

    @PostMapping("/e-statement")
    public ResponseEntity<?> addANewEstimateFacility(@RequestBody @Valid BankStatementAccountDTO bankStatementAccountDTO , @RequestParam(name="requestId") int requestId){
        return bankStatementPassBookService.estatementService(bankStatementAccountDTO,requestId);
    }

    @PutMapping("/e-statement")
    public ResponseEntity<?> updateANewEstimateFacility(@RequestBody @Valid BankStatementAccountDTO bankStatementAccountDTO , @RequestParam(name="requestId") int requestId){
        return bankStatementPassBookService.estatementService(bankStatementAccountDTO,requestId);
    }

    @PostMapping("/statement-frequncy")
    public ResponseEntity<?> statementOfFrequency(@RequestBody @Valid StatementFrequency statementFrequency, @RequestParam(name="requestId") int requestId){
        return bankStatementPassBookService.statementFrequencyService(statementFrequency,requestId);
    }

    @PutMapping("/statement-frequncy")
    public ResponseEntity<?> updateStatementOfFrequency(@RequestBody @Valid StatementFrequency statementFrequency, @RequestParam(name="requestId") int requestId){
        return bankStatementPassBookService.statementFrequencyService(statementFrequency,requestId);
    }

}
