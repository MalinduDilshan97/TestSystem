package com.spring.starter.service;

import com.spring.starter.model.Currency;
import org.springframework.http.ResponseEntity;

public interface CurrencyService {

    public ResponseEntity<?> saveCurrency(Currency currency);

    public ResponseEntity<?> updateCurrency(Currency currency);

    public ResponseEntity<?> deleteCurrency(String Currency);

    public ResponseEntity<?> searchCurrency(String Currency);

    public ResponseEntity<?> getAllCurrencys();

}
