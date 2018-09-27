package com.spring.starter.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.spring.starter.model.ReissueLoginPasswordModel;

public interface ReissueLoginPasswordModelRepository extends JpaRepository<ReissueLoginPasswordModel, Integer> {

	@Query("SELECT rl FROM ReissueLoginPasswordModel rl WHERE rl.customerServiceRequest.customerServiceRequestId=?1")
	Optional<ReissueLoginPasswordModel> getFormFromCSR(int customerServiceRequestId);
	
}
