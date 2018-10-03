package com.spring.starter.Repository;

import com.spring.starter.model.AccountStatementIssueRequest;
import com.spring.starter.model.ReIssuePinRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ReIssuePinRequestRepository extends JpaRepository<ReIssuePinRequest, Integer> {

//    @Query("SELECT r FROM ReIssuePinRequest r WHERE r.customerServiceRequest.customerServiceRequestId=?1")
//    Optional<ReIssuePinRequest> getFormFromCSR(int customerServiceRequestId);

    @Query("SELECT rip FROM ReIssuePinRequest rip WHERE rip.customerServiceRequest.customerServiceRequestId=?1")
    Optional<ReIssuePinRequest> getFormFromCSR(int customerServiceRequestId);

}
