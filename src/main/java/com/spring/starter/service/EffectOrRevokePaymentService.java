package com.spring.starter.service;

import com.spring.starter.DTO.EffectOrRevokePaymentDTO;
import org.springframework.http.ResponseEntity;

public interface EffectOrRevokePaymentService {

    public ResponseEntity<?> saveEffectOrPaymentRequest(EffectOrRevokePaymentDTO effectOrRevokePaymentDTO);

}
