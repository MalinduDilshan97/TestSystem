package com.spring.starter.Repository;

import java.util.Optional;

import com.spring.starter.model.AtmOrDebitCardRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AtmOrDebitCardRequestRepository extends JpaRepository<AtmOrDebitCardRequest, Integer> {

	@Query("SELECT aod FROM AtmOrDebitCardRequest aod WHERE aod.customerServiceRequest.customerServiceRequestId=?1")
	Optional<AtmOrDebitCardRequest> getFormFromCSR(int customerServiceRequestId);
	
}
