package com.spring.starter.Repository;

import com.spring.starter.model.LinkedAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface LinkedAccountRepository extends JpaRepository<LinkedAccount, Integer> {

    @Query("SELECT la FROM LinkedAccount la WHERE la.customerServiceRequest.customerServiceRequestId=?1")
    Optional<LinkedAccount> getFormFromCSR(int customerServiceRequestId);

}
