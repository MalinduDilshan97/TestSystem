package com.spring.starter.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.starter.DTO.BillPaymentUpdateDTO;
import com.spring.starter.model.BillPayment;
import com.spring.starter.model.BillPaymentReferance;
import com.spring.starter.service.BillPaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.Date;

@RestController
@RequestMapping("Transaction-Request/bill-payment")
public class BillPaymentController {

    @Autowired
    BillPaymentService billPaymentService;

    @PostMapping
    public ResponseEntity<?> saveBillPaymentRequest(@RequestBody @Valid BillPayment billPayment, @RequestParam(name="requestId") int requestId) {
       return billPaymentService.saveBillPayment(billPayment,requestId);
    }

    @PutMapping
    public ResponseEntity<?> updateBillPaymentRequest(@RequestBody @Valid BillPayment billPayment,@RequestParam(name="requestId") int requestId) {
        return billPaymentService.saveBillPayment(billPayment,requestId);
    }

    @GetMapping("/{billPaymentId}")
    public ResponseEntity<?> getBillPaymentRequest(@PathVariable int billPaymentId ){
        return billPaymentService.getBillPaymentRequest(billPaymentId);
    }

    @PutMapping("/update")
    public ResponseEntity<?>updateBillPaymentWithSignatures(@RequestParam  MultipartFile file,
                                                            @RequestParam  String billPaymentString,
                                                            @RequestParam int customerServiceRequestId,
                                                            @RequestParam(required = false) String comment) throws Exception {

        ObjectMapper mapper = new ObjectMapper();
        BillPayment billPayment = mapper.readValue(billPaymentString,BillPayment.class);

        BillPaymentUpdateDTO billPaymentUpdateDTO = new BillPaymentUpdateDTO();
        billPaymentUpdateDTO.setComment(comment);
        billPaymentUpdateDTO.setFile(file);

        return billPaymentService.updateBillPayment(billPayment,customerServiceRequestId,billPaymentUpdateDTO);
    }


    @GetMapping("/test")
    public ResponseEntity<?> getBillPaymentRequest(){

        Date date = java.util.Calendar.getInstance().getTime();
        BillPaymentReferance billPaymentReferance = new BillPaymentReferance();
        billPaymentReferance.setBillPaymentReferanceId(1);

        BillPayment billPayment = new BillPayment();
        billPayment.setAccountName("lakith muthugala");
        billPayment.setBenificiaryName("Senila Muthugala");
        billPayment.setBenificiaryTelNo("0710873073");
        billPayment.setBankAndBranch("Horana NDB");
        billPayment.setDate(date);
        billPayment.setCurrencyIsCash(true);
        billPayment.setCurrencyIsChaque(false);
        billPayment.setCollectionAccountNo("1234567890");
        billPayment.setBillPaymentReferance(billPaymentReferance);
        billPayment.setValueOf5000Notes(15000);
        billPayment.setValueOf2000Notes(18000);
        billPayment.setValueof1000Notes(10000);
        billPayment.setValueOf500Notes(500);
        billPayment.setValueOf100Notes(300);
        billPayment.setValueOf50Notes(150);
        billPayment.setValueOf20Notes(60);
        billPayment.setValueOf10Notes(10);
        billPayment.setValueOfcoins(5);
        billPayment.setTotal(1200000);

        return new ResponseEntity<>(billPayment,HttpStatus.OK);
    }
}
