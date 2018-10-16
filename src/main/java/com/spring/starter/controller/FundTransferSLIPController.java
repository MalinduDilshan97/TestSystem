package com.spring.starter.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.starter.DTO.DetailsUpdateDTO;
import com.spring.starter.model.FundTransferCEFT;
import com.spring.starter.model.FundTransferSLIPS;
import com.spring.starter.service.FundTransferCEFTService;
import com.spring.starter.service.FundTransferSLIPService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

@RestController
@RequestMapping("Transaction-Request/fundtrasnfet-other-slip")
public class FundTransferSLIPController {

    @Autowired
     FundTransferSLIPService fundTransferSLIPService;

    @PostMapping
    public ResponseEntity<?> addNewOtherBankSLIP(@RequestBody @Valid FundTransferSLIPS fundTransferSLIP , @RequestParam(name="requestId") int requestId) throws Exception {
        return fundTransferSLIPService.addNewFundTransferSlipRequest(fundTransferSLIP,requestId);
    }

    @PutMapping
    public ResponseEntity<?> updateNewOtherBankSLIP(@RequestBody @Valid FundTransferSLIPS fundTransferSLIPS, @RequestParam(name="requestId") int requestId) throws Exception {
        return fundTransferSLIPService.addNewFundTransferSlipRequest(fundTransferSLIPS,requestId);
    }

    @GetMapping("/{OtherbankServiceCEFTId}")
    public ResponseEntity<?> getOtherbankServiceSLIP(@PathVariable int OtherbankServiceCEFTId){
        return fundTransferSLIPService.getFundTransferSlipRequest(OtherbankServiceCEFTId);
    }

    @PutMapping("/add-signaute-and-update")
    public ResponseEntity<?> addSignatureForSLIP(@RequestParam MultipartFile file,
                                                 @RequestParam int customerTrasactionRequestId,
                                                 @RequestParam(required = false) String message) {
        return fundTransferSLIPService.addSignatureForSLIP(file,customerTrasactionRequestId);
    }

    @PutMapping("/add-files")
    public ResponseEntity<?> addFilesForSLIP(@RequestParam MultipartFile file,
                                             @RequestParam int customerTrasactionRequestId,
                                             @RequestParam String fileType){
        return fundTransferSLIPService.addFileToSLIP(file,fileType,customerTrasactionRequestId);
    }


    @PutMapping("/update")
    public ResponseEntity<?> updateCashDeposit(@RequestParam MultipartFile file,
                                               @RequestParam String fundTransfer,
                                               @RequestParam int customerServiceRequestId,
                                               @RequestParam(required = false) String comment) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        FundTransferSLIPS fundTransferSLIPS = mapper.readValue(fundTransfer, FundTransferSLIPS.class);

        DetailsUpdateDTO detailsUpdateDTO = new DetailsUpdateDTO();
        detailsUpdateDTO.setComment(comment);
        detailsUpdateDTO.setFile(file);

        return fundTransferSLIPService.updateSLIP(fundTransferSLIPS,customerServiceRequestId, detailsUpdateDTO);
    }

}
