package com.spring.starter.Repository;

import com.spring.starter.model.OtherFdCdRelatedRequest;
import com.spring.starter.model.OtherServiceRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface OtherServiceRequestRepository extends JpaRepository<OtherServiceRequest, Integer> {

    @Query("select c from OtherServiceRequest  c where c.customerServiceRequest.customerServiceRequestId=?1")
    Optional<OtherServiceRequest> findByRequestId(int requestId);
}
