package com.spring.starter.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.spring.starter.model.LinkAccountModel;

public interface LinkAccountModelRepository extends JpaRepository<LinkAccountModel, Integer> {
	@Query("SELECT la FROM LinkAccountModel la WHERE la.customerServiceRequest.customerServiceRequestId=?1")
	Optional<LinkAccountModel> getFormFromCSR(int customerServiceRequestId);	
}
