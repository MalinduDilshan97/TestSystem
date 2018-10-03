package com.spring.starter.service;

import com.spring.starter.DTO.EffectOrRevokePaymentDTO;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;

public interface EffectOrRevokePaymentService {

    public ResponseEntity<?> saveEffectOrPaymentRequest(EffectOrRevokePaymentDTO effectOrRevokePaymentDTO, HttpServletRequest request);

}
