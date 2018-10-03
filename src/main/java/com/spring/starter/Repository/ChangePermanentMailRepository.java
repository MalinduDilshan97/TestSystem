package com.spring.starter.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.spring.starter.model.ChangePermanentMail;

public interface ChangePermanentMailRepository extends JpaRepository<ChangePermanentMail, Integer> {

	@Query("SELECT cpm FROM ChangePermanentMail cpm WHERE cpm.customerServiceRequest.customerServiceRequestId=?1")
	Optional<ChangePermanentMail> getFormFromCSR(int customerServiceRequestId);
	
}
