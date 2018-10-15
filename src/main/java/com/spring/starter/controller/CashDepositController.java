package com.spring.starter.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.starter.DTO.CashWithdrawalFileDTO;
import com.spring.starter.DTO.CashWithdrawalUpdateDTO;
import com.spring.starter.DTO.TransactionSignatureDTO;
import com.spring.starter.model.CashDeposit;
import com.spring.starter.service.CashDepositService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

@RestController
@RequestMapping("Transaction-Request/cash-deposit")
public class CashDepositController {

    @Autowired
    CashDepositService cashDepositService;

    @PostMapping
    public ResponseEntity<?> saveCashDepositRequset(@RequestBody @Valid CashDeposit cashDeposit , @RequestParam(name="requestId") int requestId){
        return cashDepositService.saveNewCashDepositRequest(cashDeposit,requestId);
    }

    @PutMapping
    public ResponseEntity<?> updateCashDepositRequset(@RequestBody @Valid CashDeposit cashDeposit , @RequestParam(name="requestId") int requestId){
        return cashDepositService.saveNewCashDepositRequest(cashDeposit,requestId);
    }

    @PutMapping("/file-upload")
    public ResponseEntity<?> uploadFilesToCashDeposit(@RequestParam MultipartFile file,
                                                         @RequestParam int customerServiceRequestId,
                                                         @RequestParam String fileType) throws Exception {
        CashWithdrawalFileDTO cashWithdrawalFileDTO = new CashWithdrawalFileDTO();
        cashWithdrawalFileDTO.setCustomerTransactionRequestId(customerServiceRequestId);
        cashWithdrawalFileDTO.setFile(file);
        cashWithdrawalFileDTO.setFileType(fileType);

        return cashDepositService.uploadFilesToCashDeposit(cashWithdrawalFileDTO);
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateCashDeposit(@RequestParam MultipartFile file,
                                                  @RequestParam String cashDeposit,
                                                  @RequestParam int customerServiceRequestId,
                                                  @RequestParam(required = false) String comment
    ) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        CashDeposit deposit = mapper.readValue(cashDeposit, CashDeposit.class);

        CashWithdrawalUpdateDTO cashWithdrawalUpdateDTO = new CashWithdrawalUpdateDTO();
        cashWithdrawalUpdateDTO.setComment(comment);
        cashWithdrawalUpdateDTO.setFile(file);

        return cashDepositService.updateCashDeposit(deposit,customerServiceRequestId,cashWithdrawalUpdateDTO);
    }



    @GetMapping("/{cashDepositId}")
    public ResponseEntity<?> getCashDepositRequestForm(@PathVariable int cashDepositId){
        return cashDepositService.getCashDepositRequest(cashDepositId);
    }

    @PutMapping("/signature")
    public ResponseEntity<?> addMethodSignature(@RequestParam MultipartFile file,
                                                @RequestParam int customerServiceRequestId,
                                                @RequestParam String message) throws Exception {

        TransactionSignatureDTO transactionSignatureDTO = new TransactionSignatureDTO();
        transactionSignatureDTO.setCustomerTransactionId(customerServiceRequestId);
        int q;
        transactionSignatureDTO.setMessage(message);
        transactionSignatureDTO.setFile(file);
        return  cashDepositService.saveTrasnsactionSignature(transactionSignatureDTO);
    }

    @GetMapping ("/test")
    public ResponseEntity<?> test(){
        CashDeposit cashDeposit = new CashDeposit();
        cashDeposit.setAccountNumber("Lakith Muthugala");
        cashDeposit.setAccountNumber("123456789");
        cashDeposit.setNameOfDepositor("Senila Muthugala");
        cashDeposit.setAddress("Thilakavilla,Thuttiripitiya,Halthota,Bandaragama");
        cashDeposit.setIdentification("951160164V");
        cashDeposit.setPurposeOfDeposit("Just..");
        cashDeposit.setAmmountInWords("five thousand only");
        cashDeposit.setPhoneNumberAndExtn("0342252011");
        cashDeposit.setDate(java.util.Calendar.getInstance().getTime());
        cashDeposit.setCurrency("LKR");
        cashDeposit.setValueOf5000Notes(15000);
        cashDeposit.setValueOf2000Notes(18000);
        cashDeposit.setValueof1000Notes(10000);
        cashDeposit.setValueOf500Notes(500);
        cashDeposit.setValueOf100Notes(300);
        cashDeposit.setValueOf50Notes(150);
        cashDeposit.setValueOf20Notes(60);
        cashDeposit.setValueOf10Notes(10);
        cashDeposit.setValueOfcoins(5);
        cashDeposit.setTotal(1200000);

        return new ResponseEntity<>(cashDeposit,HttpStatus.OK);
    }
}
