package com.spring.starter.service;

import com.spring.starter.model.BillPayment;
import org.springframework.http.ResponseEntity;

public interface BillPaymentService {

    public ResponseEntity<?> saveBillPayment(BillPayment billPayment, int customerTransactionRequestId);

}
