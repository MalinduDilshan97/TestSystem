package com.spring.starter.controller;

import com.spring.starter.model.Currency;
import com.spring.starter.service.CurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("Currency")
public class CurrencyController {

    @Autowired
    private CurrencyService currencyService;

    @PostMapping("/save")
    public ResponseEntity<?> saveCurrency(@RequestBody Currency currency){
        return currencyService.saveCurrency(currency);
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateCurrency(@RequestBody Currency currency){
        return currencyService.updateCurrency(currency);
    }

    @DeleteMapping("/delete/{currency}")
    public ResponseEntity<?> deleteCurrency(@PathVariable String currency){
        return currencyService.deleteCurrency(currency);
    }

    @GetMapping("/search/{currency}")
    public ResponseEntity<?> getCurrency(@PathVariable String currency){
        return currencyService.searchCurrency(currency);
    }

    @GetMapping("get-all")
    public ResponseEntity<?> getAllCurrency(){
        return currencyService.getAllCurrencys();
    }

}
