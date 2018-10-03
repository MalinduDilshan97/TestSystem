package com.spring.starter.Repository;

import com.spring.starter.model.EstatementFacility;
import com.spring.starter.model.StatementFrequency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface StatementFrequencyRepository extends JpaRepository<StatementFrequency, Integer> {

    @Query("SELECT sf FROM StatementFrequency sf WHERE sf.customerServiceRequest.customerServiceRequestId=?1")
    Optional<StatementFrequency> getFormFromCSR(int customerServiceRequestId);

}
