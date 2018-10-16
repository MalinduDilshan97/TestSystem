package com.spring.starter.service.impl;

import com.spring.starter.Exception.CustomException;
import com.spring.starter.Repository.CurrencyRepository;
import com.spring.starter.model.Currency;
import com.spring.starter.model.ResponseModel;
import com.spring.starter.service.CurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CurrencyServiceImpl implements CurrencyService {

    @Autowired
    private CurrencyRepository currencyRepository;
    private ResponseModel response = new ResponseModel();

    @Override
    public ResponseEntity<?> saveCurrency(Currency currency) {
        try {
            if (currencyRepository.save(currency)!=null){
                response.setMessage(" Request Successful ");
                response.setStatus(true);
                return new ResponseEntity<>(response, HttpStatus.CREATED);
            }else{
                response.setMessage(" Request  Unsuccessful");
                response.setStatus(false);
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            throw new CustomException("Request Unsuccessful");
        }
    }

    @Override
    public ResponseEntity<?> updateCurrency(Currency currency) {
        try {
            Optional<Currency> optional=currencyRepository.findById(currency.getCurrency_id());
            if (!optional.isPresent()){
                response.setMessage(" No Data Found");
                response.setStatus(false);
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }

            currency.setCurrency_id(optional.get().getCurrency_id());

            if (currencyRepository.save(currency)!=null){
                response.setMessage(" Updated Successful ");
                response.setStatus(true);
                return new ResponseEntity<>(response, HttpStatus.OK);
            }else{
                response.setMessage(" Request  Unsuccessful");
                response.setStatus(false);
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            throw new CustomException("Request Unsuccessful");
        }

    }

    @Override
    public ResponseEntity<?> deleteCurrency(String currency) {
        try {
            Optional<Currency> optional=currencyRepository.getCurrency(currency);
            if (!optional.isPresent()){
                response.setMessage(" No Data Found");
                response.setStatus(false);
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }
            currencyRepository.deleteById(optional.get().getCurrency_id());
            response.setMessage(" Request Successful");
            response.setStatus(true);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            throw new CustomException("Request Unsuccessful");
        }
    }

    @Override
    public ResponseEntity<?> searchCurrency(String currency) {
        Optional<Currency> optional=currencyRepository.getCurrency(currency);
        if (!optional.isPresent()){
            response.setMessage(" No Data Found");
            response.setStatus(false);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(optional.get(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> getAllCurrencys() {
        List<Currency>currencyList=currencyRepository.findAll();
        if (currencyList.isEmpty()){
            response.setMessage(" No Data Found");
            response.setStatus(false);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(currencyList,HttpStatus.OK);
    }
}
