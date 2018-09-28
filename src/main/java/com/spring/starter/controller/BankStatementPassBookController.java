package com.spring.starter.controller;

import com.spring.starter.DTO.AccountStatementIssueRequestDTO;
import com.spring.starter.DTO.DuplicatePassBookRequestDTO;
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
    public ResponseEntity<?> duplicatePassBookRequest(@RequestBody DuplicatePassBookRequestDTO duplicatePassBookRequestDTO){
        return bankStatementPassBookService.duplicatePassBookRequest(duplicatePassBookRequestDTO);
    }

    @PostMapping("/AccountStatemnt")
    public ResponseEntity<?> AccountStatementRequest(@RequestBody AccountStatementIssueRequestDTO accountStatementIssueRequestDTO){
        return bankStatementPassBookService.AccountStatement(accountStatementIssueRequestDTO);
    }

}
