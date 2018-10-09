package com.spring.starter.Repository;

import com.spring.starter.model.FundTransferWithinNDB;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface FundTransferWithinNDBRepository extends JpaRepository<FundTransferWithinNDB,Integer> {

    @Query("SELECT ftwn FROM FundTransferWithinNDB ftwn WHERE ftwn.customerTransactionRequest.customerTransactionRequestId=?1")
    Optional<FundTransferWithinNDB> getFormFromCSR(int customerTransactionRequestID);

}
