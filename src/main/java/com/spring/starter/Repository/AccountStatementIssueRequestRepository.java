package com.spring.starter.Repository;

import com.spring.starter.model.AccountStatementIssueRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface AccountStatementIssueRequestRepository extends JpaRepository<AccountStatementIssueRequest,Integer> {

    @Query("SELECT asir FROM AccountStatementIssueRequest asir WHERE asir.customerServiceRequest.customerServiceRequestId=?1")
    Optional<AccountStatementIssueRequest> getFormFromCSR(int customerServiceRequestId);

}
