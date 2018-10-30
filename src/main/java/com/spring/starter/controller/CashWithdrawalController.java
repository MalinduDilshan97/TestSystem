package com.spring.starter.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.starter.DTO.CashWithdrawalDTO;
import com.spring.starter.DTO.FileDTO;
import com.spring.starter.DTO.DetailsUpdateDTO;
import com.spring.starter.DTO.TransactionSignatureDTO;
import com.spring.starter.service.CashWithdrawalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

@RestController
@RequestMapping("Transaction-Request/cash-withdrawal")
public class CashWithdrawalController {

    @Autowired
    private CashWithdrawalService cashWithdrawalService;

    @PostMapping
    public ResponseEntity<?> cashWithdrawalSave(@Valid @RequestBody CashWithdrawalDTO cashWithdrawalDTO,
                                                @RequestParam(name="requestId") int requestId){
        return cashWithdrawalService.cashWithdrawal(cashWithdrawalDTO,requestId);
    }

    @PutMapping
    public ResponseEntity<?> cashWithdrawalUpdate(@Valid @RequestBody CashWithdrawalDTO cashWithdrawalDTO,
                                                  @RequestParam(name="requestId") int requestId){
        return cashWithdrawalService.cashWithdrawal(cashWithdrawalDTO,requestId);
    }

    @PutMapping("/file-upload")
    public ResponseEntity<?> uploadFilesToCashWithdwales(@RequestParam MultipartFile file,
                                                         @RequestParam int customerServiceRequestId,
                                                         @RequestParam String fileType) throws Exception {
        FileDTO fileDTO = new FileDTO();
        fileDTO.setCustomerTransactionRequestId(customerServiceRequestId);
        fileDTO.setFile(file);
        fileDTO.setFileType(fileType);

        return cashWithdrawalService.uploadFilesToCashWithdrawls(fileDTO);
    }

    @PutMapping("/signature")
    public ResponseEntity<?> addMethodSignature(@RequestParam MultipartFile file,
                                                @RequestParam int customerTrasactionRequestId,
                                                @RequestParam(required = false) String message) throws Exception {

        TransactionSignatureDTO transactionSignatureDTO = new TransactionSignatureDTO();
        transactionSignatureDTO.setCustomerTransactionId(customerTrasactionRequestId);
        transactionSignatureDTO.setMessage(message);
        transactionSignatureDTO.setFile(file);

        return  cashWithdrawalService.saveTrasnsactionSignature(transactionSignatureDTO);
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateCashWithDrawal(@RequestParam MultipartFile file,
                                                 @RequestParam String cashWithdrawal,
                                                 @RequestParam int customerServiceRequestId,
                                                 @RequestParam(required = false) String comment
                                                 ) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        CashWithdrawalDTO cashWithdrawalDTO = mapper.readValue(cashWithdrawal, CashWithdrawalDTO.class);

        DetailsUpdateDTO detailsUpdateDTO = new DetailsUpdateDTO();
        detailsUpdateDTO.setComment(comment);
        detailsUpdateDTO.setFile(file);

        return cashWithdrawalService.updateCashWithdrawal(cashWithdrawalDTO,customerServiceRequestId, detailsUpdateDTO);
    }

    @GetMapping("cash-withdrawal-update-records")
    public ResponseEntity<?> getBillPaymentUpdateRecords(@RequestParam(name="requestId") int requestId) {
        return cashWithdrawalService.getCashWithdrawalUpdateRecords(requestId);
    }

}
