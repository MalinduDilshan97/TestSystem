package com.spring.starter.Repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.spring.starter.model.CustomerServiceRequest;

public interface CustomerServiceRequestRepository extends JpaRepository<CustomerServiceRequest, Integer> {

	@Query("SELECT cs FROM CustomerServiceRequest cs WHERE cs.customer.customerId = ?1 AND date(cs.requestDate)=?2")
	List<CustomerServiceRequest> getrequestsByDateAndCustomer(int customerId, Date requestDate);
	
	@Query("SELECT cs FROM CustomerServiceRequest cs WHERE cs.customer.customerId = ?1")
	List<CustomerServiceRequest> getAllCustomerRequest(int customerId);
	
}
