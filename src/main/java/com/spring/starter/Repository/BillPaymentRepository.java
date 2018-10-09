package com.spring.starter.Repository;

import com.spring.starter.model.BillPayment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface BillPaymentRepository extends JpaRepository<BillPayment, Integer> {

    @Query("SELECT bp FROM BillPayment bp WHERE bp.customerTransactionRequest.customerTransactionRequestId=?1")
    Optional<BillPayment> getFormFromCSR(int customerTransactionRequestID);
}
