package com.spring.starter.controller;

import com.spring.starter.DTO.CashWithdrawalDTO;
import com.spring.starter.service.CashWithdrawalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("Transaction-Request/cash-withdrawal")
public class CashWithdrawalController {

    @Autowired
    private CashWithdrawalService cashWithdrawalService;

    @PostMapping
    public ResponseEntity<?> cashWithdrawalSave(@Valid @RequestBody CashWithdrawalDTO cashWithdrawalDTO, @RequestParam(name="requestId") int requestId){
        return cashWithdrawalService.cashWithdrawal(cashWithdrawalDTO,requestId);
    }

    @PutMapping
    public ResponseEntity<?> cashWithdrawalUpdate(@Valid @RequestBody CashWithdrawalDTO cashWithdrawalDTO, @RequestParam(name="requestId") int requestId){
        return cashWithdrawalService.cashWithdrawal(cashWithdrawalDTO,requestId);
    }

}
