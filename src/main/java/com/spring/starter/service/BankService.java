package com.spring.starter.service;

import com.spring.starter.model.Bank;
import org.springframework.http.ResponseEntity;

public interface BankService {

    public ResponseEntity<?> saveBank(Bank bank);

    public ResponseEntity<?> updateBank(Bank bank);

    public ResponseEntity<?> deleteBank(int mx_bank_code);

    public ResponseEntity<?> searchBank(int mx_bank_code);

    public ResponseEntity<?> getAllBanks();

}
