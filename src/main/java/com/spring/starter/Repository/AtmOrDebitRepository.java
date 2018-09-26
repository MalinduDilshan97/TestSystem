package com.spring.starter.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.spring.starter.model.AtmOrDebit;

public interface AtmOrDebitRepository extends JpaRepository<AtmOrDebit, Integer> {

	@Query("SELECT aod FROM AtmOrDebit aod WHERE aod.customerServiceRequest.customerServiceRequestId=?1")
	Optional<AtmOrDebit> getFormFromCSR(int customerServiceRequestId);
	
}
