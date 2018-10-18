package com.spring.starter.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.spring.starter.model.Customer;

import javax.xml.crypto.Data;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {

	@Query("SELECT c FROM Customer c WHERE c.identification=?1")
	Optional<Customer> getCustomerFromIdentity(String identification);

	@Query("SELECT c FROM Customer c WHERE date(c.date)=?1 and c.status = 0")
	List<Customer> getCustomerRequestDetails(Date date);
	
}
