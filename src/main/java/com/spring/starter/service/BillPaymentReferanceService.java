package com.spring.starter.service;

import com.spring.starter.DTO.BillPaymentReferanceDTO;
import com.spring.starter.model.BillPaymentReferance;
import org.springframework.http.ResponseEntity;

public interface BillPaymentReferanceService {

    public ResponseEntity<?> saveBillPaymentReferance(BillPaymentReferanceDTO billPaymentReferance);

    public ResponseEntity<?> getAllBillPaymentReferance();

    public ResponseEntity<?> updateBillPaymentReferance(BillPaymentReferanceDTO billPaymentReferance,
                                                        int billpaymentReferanceId);

    public ResponseEntity<?> searchBillPayemtnReferance(int billpaymentReferanceId);

    public ResponseEntity<?> deleteBillReferance(int billpaymentReferanceId);

}
