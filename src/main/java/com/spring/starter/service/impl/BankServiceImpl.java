package com.spring.starter.service.impl;

import com.spring.starter.Exception.CustomException;
import com.spring.starter.Repository.BankRepository;
import com.spring.starter.model.Bank;
import com.spring.starter.model.ResponseModel;
import com.spring.starter.service.BankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;


@Service
@Transactional
public class BankServiceImpl implements BankService {

    @Autowired
    private BankRepository bankRepository;
    private ResponseModel response = new ResponseModel();

    @Override
    public ResponseEntity<?> saveBank(Bank bank) {
        try {
            if (bankRepository.save(bank)!=null){
                response.setMessage(" Request Successful");
                response.setStatus(true);
                return new ResponseEntity<>(response, HttpStatus.CREATED);
            }else{
                response.setMessage(" Request Unsuccessful");
                response.setStatus(false);
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            throw new CustomException("Request Unsuccessful");
        }
    }

    @Override
    public ResponseEntity<?> updateBank(Bank bank) {
        try {
            Optional<Bank>  optional=bankRepository.findById(bank.getMx_bank_code());
            if (!optional.isPresent()){
                response.setMessage(" No Data Found");
                response.setStatus(false);
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }
            bank.setBranchList(optional.get().getBranchList());
            bank=bankRepository.save(bank);
            if (bank==null){
                response.setMessage(" Request Unsuccessful");
                response.setStatus(false);
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }else{
                response.setMessage(" Request Successful");
                response.setStatus(true);
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
        } catch (Exception e) {
            throw new CustomException("Request Unsuccessful");
        }
    }

    @Override
    public ResponseEntity<?> deleteBank(int mx_bank_code) {
        try {
            Optional<Bank>  optional=bankRepository.findById(mx_bank_code);
            if (!optional.isPresent()){
                response.setMessage(" No Data Found");
                response.setStatus(false);
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }
            bankRepository.deleteById(mx_bank_code);
            response.setMessage(" Request Successful");
            response.setStatus(true);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            throw new CustomException("Request Unsuccessful");
        }
    }

    @Override
    public ResponseEntity<?> searchBank(int mx_bank_code) {
        try {
            Optional<Bank>  optional=bankRepository.findById(mx_bank_code);
            if (!optional.isPresent()){
                response.setMessage(" No Data Found");
                response.setStatus(false);
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>(optional.get(),HttpStatus.OK);
        } catch (Exception e) {
            throw new CustomException("Request Unsuccessful");
        }
    }

    @Override
    public ResponseEntity<?> getAllBanks() {
        try {
            List<Bank> bankList=bankRepository.findAll();
            if (bankList.isEmpty()){
                response.setMessage(" No Data Found");
                response.setStatus(false);
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }

            return new ResponseEntity<>(bankList,HttpStatus.OK);
        } catch (Exception e) {
            throw new CustomException("Request Unsuccessful");
        }
    }


}
