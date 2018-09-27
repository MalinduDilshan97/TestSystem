package com.spring.starter.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.spring.starter.model.ChangeMailingMailModel;

public interface ChangeMailingMailModelRepository extends JpaRepository<ChangeMailingMailModel, Integer> {

	@Query("SELECT cmm FROM ChangeMailingMailModel cmm WHERE cmm.customerServiceRequest.customerServiceRequestId=?1")
	Optional<ChangeMailingMailModel> getFormFromCSR(int customerServiceRequestId);
	
}
