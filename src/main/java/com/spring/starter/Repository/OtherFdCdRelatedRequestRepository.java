package com.spring.starter.Repository;

import com.spring.starter.model.OtherFdCdRelatedRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface OtherFdCdRelatedRequestRepository extends JpaRepository<OtherFdCdRelatedRequest,Integer> {

    @Query("select c from OtherFdCdRelatedRequest  c where c.customerServiceRequest.customerServiceRequestId=?1")
    Optional<OtherFdCdRelatedRequest> findByRequestId(int requestId);
}
