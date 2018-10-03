package com.spring.starter.Repository;

import com.spring.starter.model.DuplicatePassBookRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface DuplicatePassBookRequestRepository extends JpaRepository<DuplicatePassBookRequest, Integer> {

    @Query("SELECT dpr FROM DuplicatePassBookRequest dpr WHERE dpr.customerServiceRequest.customerServiceRequestId=?1")
    Optional<DuplicatePassBookRequest> getFormFromCSR(int customerServiceRequestId);

}
