package com.spring.starter.controller;

import com.spring.starter.DTO.AccountStatementIssueRequestDTO;
import com.spring.starter.DTO.BankStatementAccountDTO;
import com.spring.starter.DTO.DuplicatePassBookRequestDTO;
import com.spring.starter.model.StatementFrequency;
import com.spring.starter.service.BankStatementPassBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bankStatementPassBook")
@CrossOrigin
public class BankStatementPassBookController {

    @Autowired
    private BankStatementPassBookService bankStatementPassBookService;

    @PostMapping("/duplicatePassbook")
    public ResponseEntity<?> duplicatePassBookRequest(@RequestBody DuplicatePassBookRequestDTO duplicatePassBookRequestDTO) {
        return bankStatementPassBookService.duplicatePassBookRequest(duplicatePassBookRequestDTO);
    }

    @PutMapping("/duplicatePassbook")
    public ResponseEntity<?> duplicatePassBookRequestUpdate(@RequestBody DuplicatePassBookRequestDTO duplicatePassBookRequestDTO) {
        return bankStatementPassBookService.duplicatePassBookRequest(duplicatePassBookRequestDTO);
    }

    @PostMapping("/AccountStatement")
    public ResponseEntity<?> AccountStatementRequest(@RequestBody AccountStatementIssueRequestDTO accountStatementIssueRequestDTO) {
        return bankStatementPassBookService.AccountStatement(accountStatementIssueRequestDTO);
    }

    @PutMapping("/AccountStatement")
    public ResponseEntity<?> AccountStatementRequestUpdate(@RequestBody AccountStatementIssueRequestDTO accountStatementIssueRequestDTO) {
        return bankStatementPassBookService.AccountStatement(accountStatementIssueRequestDTO);
    }

    @PostMapping("/e-statement")
    public ResponseEntity<?> e_satementRequest(@RequestBody BankStatementAccountDTO bankStatementAccountDTO, @RequestParam(name = "requestId") int requestId) {
        return bankStatementPassBookService.estatementService(bankStatementAccountDTO, requestId);
    }

    @PostMapping("/statementFrequency")
    public ResponseEntity<?> statementFrequencyServiceRequest(@RequestBody StatementFrequency statementFrequency, @RequestParam(name = "requestId") int requestId) {
        return bankStatementPassBookService.statementFrequencyService(statementFrequency, requestId);
    }


}
