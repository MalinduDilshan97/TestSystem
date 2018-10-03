package com.spring.starter.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.spring.starter.model.InternetBanking;

public interface InternetBankingRepository extends JpaRepository<InternetBanking, Integer> {

    @Query("SELECT ib FROM InternetBanking ib WHERE ib.customerServiceRequest.customerServiceRequestId=?1")
    Optional<InternetBanking> getFormFromCSR(int customerServiceRequestId);

}
