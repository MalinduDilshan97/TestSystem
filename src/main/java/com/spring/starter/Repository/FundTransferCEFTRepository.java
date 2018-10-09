package com.spring.starter.Repository;

import com.spring.starter.model.FundTransferCEFT;
import com.spring.starter.model.FundTransferSLIPS;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface FundTransferCEFTRepository extends JpaRepository<FundTransferCEFT, Integer> {

    @Query("SELECT ft FROM FundTransferCEFT ft WHERE ft.customerTransactionRequest.customerTransactionRequestId=?1")
    Optional<FundTransferCEFT> getFormFromCSR(int customerTransactionRequestID);

}
