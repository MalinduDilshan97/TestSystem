package com.spring.starter.controller;

import com.spring.starter.DTO.EffectOrRevokePaymentDTO;
import com.spring.starter.service.EffectOrRevokePaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/effectOrRevokePayment")
public class EffectOrRevokePaymentController {

    @Autowired
    private EffectOrRevokePaymentService effectOrRevokePaymentService;

    @PostMapping("/save")
    public ResponseEntity<?> createAEffectOrRevokePaymentRequest(@RequestBody EffectOrRevokePaymentDTO effectOrRevokePaymentDTO){
        return effectOrRevokePaymentService.saveEffectOrPaymentRequest(effectOrRevokePaymentDTO);
    }

    @PutMapping("/update")
    public ResponseEntity<?> effectOrRevokePaymentRequestUpdate(@RequestBody EffectOrRevokePaymentDTO effectOrRevokePaymentDTO){
        return effectOrRevokePaymentService.saveEffectOrPaymentRequest(effectOrRevokePaymentDTO);
    }
}