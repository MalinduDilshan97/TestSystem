package com.spring.starter.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.spring.starter.model.CustomerServiceRequestForm;

public interface CustomerServiceRequestFormRepository extends JpaRepository<CustomerServiceRequestForm, Integer>{

	/*@Query("SELECT csr FROM CustomerServiceRequestForm csr WHERE csr.customerServiceRequest.customerServiceRequestId=?1")
	Optional<CustomerServiceRequestForm> getCustomerServiceRequestFormForCustomerServiceRequest(int customerServiceRequestId);*/
}
