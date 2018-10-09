package com.spring.starter.Repository;

import com.spring.starter.model.BillPayment;
import com.spring.starter.model.FundTransferSLIPS;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface FundTransferSLIPRepository extends JpaRepository<FundTransferSLIPS , Integer> {

    @Query("SELECT ft FROM FundTransferSLIPS ft WHERE ft.customerTransactionRequest.customerTransactionRequestId=?1")
    Optional<FundTransferSLIPS> getFormFromCSR(int customerTransactionRequestID);
}
