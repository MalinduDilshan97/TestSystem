package com.spring.starter.service;

import com.spring.starter.DTO.BillPaymentUpdateDTO;
import com.spring.starter.model.BillPayment;
import org.springframework.http.ResponseEntity;

public interface BillPaymentService {

    public ResponseEntity<?> saveBillPayment(BillPayment billPayment, int customerTransactionRequestId);

    public ResponseEntity<?> getBillPaymentRequest(int billPaymentId);

    public ResponseEntity<?> updateBillPayment(BillPayment billPayment, int customerTransactionRequestId,
                                               BillPaymentUpdateDTO billPaymentUpdateDTO) throws Exception;

}
