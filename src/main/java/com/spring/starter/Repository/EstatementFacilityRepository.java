package com.spring.starter.Repository;

import com.spring.starter.model.EstatementFacility;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface EstatementFacilityRepository extends JpaRepository<EstatementFacility, Integer> {

    @Query("SELECT ef FROM EstatementFacility ef WHERE ef.customerServiceRequest.customerServiceRequestId=?1")
    Optional<EstatementFacility> getFormFromCSR(int customerServiceRequestId);

}
