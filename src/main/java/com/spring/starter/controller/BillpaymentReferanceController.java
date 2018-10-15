package com.spring.starter.controller;

import com.spring.starter.DTO.BillPaymentReferanceDTO;
import com.spring.starter.service.BillPaymentReferanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("Transaction-Request/bill-payment-referance")
public class BillpaymentReferanceController {

    @Autowired
    BillPaymentReferanceService billPaymentReferanceService;

    @PostMapping("/add-new-bill-referance")
    public ResponseEntity<?> addNewBankRefarance(@RequestBody BillPaymentReferanceDTO billPaymentReferanceDTO){
       return billPaymentReferanceService.saveBillPaymentReferance(billPaymentReferanceDTO);
    }

    @PutMapping("/update-bill-referance/{billpaymentReferanceid}")
    public ResponseEntity<?> updateNewBankRefarance(@RequestBody BillPaymentReferanceDTO billPaymentReferanceDTO, @PathVariable int billpaymentReferanceid){
        return billPaymentReferanceService.updateBillPaymentReferance(billPaymentReferanceDTO,billpaymentReferanceid);
    }

    @GetMapping("/get-bill-referances")
    public ResponseEntity<?> viewBillReferanceDetails(){
        return billPaymentReferanceService.getAllBillPaymentReferance();
    }

    @GetMapping("/search-bill-referances/{billpaymentReferanceid}")
    public ResponseEntity<?> searchBillReferanceDetails(@PathVariable int billpaymentReferanceid){
        return billPaymentReferanceService.searchBillPayemtnReferance(billpaymentReferanceid);
    }

    @DeleteMapping("/delete-bill-referances/{billpaymentReferanceid}")
    public ResponseEntity<?> deleteBillReferanceDetails(@PathVariable int billpaymentReferanceid){
        return billPaymentReferanceService.deleteBillReferance(billpaymentReferanceid);
    }

    @GetMapping("/get-bill-payment-referance")
    public ResponseEntity<?> searchBillReferanceDetails(){
        return billPaymentReferanceService.getAllBillPaymentReferance();
    }

    @GetMapping("/test")
    public ResponseEntity<?> getBillpaymentTest(){
        BillPaymentReferanceDTO billPaymentReferanceDTO = new BillPaymentReferanceDTO();
        billPaymentReferanceDTO.setReferanceType("Dialog Axiata PLC Mobile - Telephone");
        billPaymentReferanceDTO.setCollectionAccountNo("101000120848");
        billPaymentReferanceDTO.setBillerNickname("DIALOG MOBILE");
        billPaymentReferanceDTO.setLocation("Colombo");
        billPaymentReferanceDTO.setBillerType("Online");
        billPaymentReferanceDTO.setCollectionAccountType("Current Acc");
        billPaymentReferanceDTO.setCollectionAccountBankAndBranch("Head Office");
        billPaymentReferanceDTO.setMaximumAmmountCanPay(50);
        billPaymentReferanceDTO.setMaximumAmmountCanPay(500000);
        billPaymentReferanceDTO.setBillerContactEmail("global@dialog.lk");
        billPaymentReferanceDTO.setBillerContactPhoneNumber("1777");
        billPaymentReferanceDTO.setInputField_1("Phone No");
        billPaymentReferanceDTO.setInputField_2("10-10");
        billPaymentReferanceDTO.setInputField_3(null);
        billPaymentReferanceDTO.setInputField_4(null);
        billPaymentReferanceDTO.setInputField_5(null);
        billPaymentReferanceDTO.setValidationRule_1(null);
        billPaymentReferanceDTO.setValidationRule_2(null);
        billPaymentReferanceDTO.setValidationRule_3(null);
        billPaymentReferanceDTO.setValidationRule_4(null);
        billPaymentReferanceDTO.setValidationRule_5(null);
        billPaymentReferanceDTO.setComment(null);

        return new ResponseEntity<>(billPaymentReferanceDTO,HttpStatus.OK);
    }
}
