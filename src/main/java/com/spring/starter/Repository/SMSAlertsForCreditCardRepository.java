package com.spring.starter.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.spring.starter.model.SMSAlertsForCreditCard;

public interface SMSAlertsForCreditCardRepository extends JpaRepository<SMSAlertsForCreditCard, Integer>{
	
	@Query("SELECT s FROM SMSAlertsForCreditCard s WHERE s.customerServiceRequest.customerServiceRequestId=?1")
	Optional<SMSAlertsForCreditCard> getFormFromCSR(int customerServiceRequestId);
}
