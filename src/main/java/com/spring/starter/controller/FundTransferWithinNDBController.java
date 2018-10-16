package com.spring.starter.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.starter.DTO.DetailsUpdateDTO;
import com.spring.starter.DTO.FileDTO;
import com.spring.starter.DTO.FundTransferWithinNDBDTO;
import com.spring.starter.DTO.TransactionSignatureDTO;
import com.spring.starter.service.FundTransferWithinNDBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

@RestController
@RequestMapping("Transaction-Request/fund-transaction-within-ndb")
public class FundTransferWithinNDBController {

    @Autowired
    private FundTransferWithinNDBService fundTransferWithinNDBService;

    @PostMapping
    public ResponseEntity<?> saveFundTransferWithinNDBRequest(@Valid @RequestBody FundTransferWithinNDBDTO transferWithinNDBDTO, @RequestParam(name="requestId") int requestId){
        return fundTransferWithinNDBService.saveFundTransferWithinNDBRequest(transferWithinNDBDTO,requestId);
    }

    @PutMapping
    public ResponseEntity<?> updateFundTransferWithinNDBRequest(@Valid @RequestBody FundTransferWithinNDBDTO transferWithinNDBDTO,@RequestParam(name="requestId") int requestId){
        return fundTransferWithinNDBService.saveFundTransferWithinNDBRequest(transferWithinNDBDTO,requestId);
    }

    @PutMapping("/file-upload")
    public ResponseEntity<?> uploadFilesToCashDeposit(@RequestParam MultipartFile file,
                                                      @RequestParam int customerServiceRequestId,
                                                      @RequestParam String fileType) throws Exception {
        FileDTO fileDTO = new FileDTO();
        fileDTO.setCustomerTransactionRequestId(customerServiceRequestId);
        fileDTO.setFile(file);
        fileDTO.setFileType(fileType);

        return fundTransferWithinNDBService.uploadFilesToFundTransferWithinNDBRequest(fileDTO);
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateCashDeposit(@RequestParam MultipartFile file,
                                               @RequestParam String ndb,
                                               @RequestParam int customerServiceRequestId,
                                               @RequestParam(required = false) String comment
    ) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        FundTransferWithinNDBDTO transferWithinNDBDTO = mapper.readValue(ndb, FundTransferWithinNDBDTO.class);

        DetailsUpdateDTO detailsUpdateDTO = new DetailsUpdateDTO();
        detailsUpdateDTO.setComment(comment);
        detailsUpdateDTO.setFile(file);

        return fundTransferWithinNDBService.updateFundTransferWithinNDBRequest(transferWithinNDBDTO,customerServiceRequestId, detailsUpdateDTO);
    }

    @PutMapping("/signature")
    public ResponseEntity<?> addMethodSignature(@RequestParam MultipartFile file,
                                                @RequestParam int customerServiceRequestId,
                                                @RequestParam String message) throws Exception {

        TransactionSignatureDTO transactionSignatureDTO = new TransactionSignatureDTO();
        transactionSignatureDTO.setCustomerTransactionId(customerServiceRequestId);
        transactionSignatureDTO.setMessage(message);
        String s="";
        transactionSignatureDTO.setFile(file);
        return  fundTransferWithinNDBService.saveTrasnsactionSignature(transactionSignatureDTO);
    }
}
