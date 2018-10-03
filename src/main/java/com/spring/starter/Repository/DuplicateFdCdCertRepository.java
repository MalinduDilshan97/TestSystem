package com.spring.starter.Repository;

import com.spring.starter.model.DuplicateFdCdCert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface DuplicateFdCdCertRepository extends JpaRepository<DuplicateFdCdCert, Integer> {
    @Query("select c from DuplicateFdCdCert  c where c.customerServiceRequest.customerServiceRequestId=?1")
    Optional<DuplicateFdCdCert> findByRequestId(int requestId);
}
