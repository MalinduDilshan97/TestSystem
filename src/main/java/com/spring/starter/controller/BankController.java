package com.spring.starter.controller;

import com.spring.starter.model.Bank;
import com.spring.starter.service.BankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/Bank")
public class BankController {

    @Autowired
    private BankService bankService;

    @PostMapping("/save")
    public ResponseEntity<?> saveBank(@RequestBody @Valid Bank bank){
        return bankService.saveBank(bank);
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateBank(@RequestBody @Valid Bank bank){
        return bankService.updateBank(bank);
    }

    @DeleteMapping("/delete/{mx_bank_code}")
    public ResponseEntity<?> deleteBank(@PathVariable int mx_bank_code){
        return bankService.deleteBank(mx_bank_code);
    }

    @GetMapping("/search-bank/{mx_bank_code}")
    public ResponseEntity<?> getBank(@PathVariable int mx_bank_code){
        return bankService.searchBank(mx_bank_code);
    }

    @GetMapping("get-all-banks")
    public ResponseEntity<?> getAllBanks(){
        return bankService.getAllBanks();
    }

}
