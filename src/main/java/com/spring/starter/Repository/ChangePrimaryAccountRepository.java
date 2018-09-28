package com.spring.starter.Repository;

import com.spring.starter.model.ChangePrimaryAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ChangePrimaryAccountRepository extends JpaRepository<ChangePrimaryAccount,Integer> {

    @Query("SELECT cpa FROM ChangePrimaryAccount cpa WHERE cpa.customerServiceRequest.customerServiceRequestId=?1")
    Optional<ChangePrimaryAccount> getFormFromCSR(int customerServiceRequestId);

}
