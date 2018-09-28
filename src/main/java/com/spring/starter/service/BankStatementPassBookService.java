package com.spring.starter.service;

import com.spring.starter.DTO.AccountStatementIssueRequestDTO;
import com.spring.starter.DTO.DuplicatePassBookRequestDTO;
import org.springframework.http.ResponseEntity;

public interface BankStatementPassBookService {

    public ResponseEntity<?> duplicatePassBookRequest(DuplicatePassBookRequestDTO duplicatePassBookRequestDTO);

    public ResponseEntity<?> AccountStatement(AccountStatementIssueRequestDTO accountStatementIssueRequestDTO);

}
