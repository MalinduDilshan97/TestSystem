package com.spring.starter.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.spring.starter.model.ExcludeInternetAccount;

public interface ExcludeInternetAccountRepository extends JpaRepository<ExcludeInternetAccount, Integer> {

	@Query("SELECT eia FROM ExcludeInternetAccount eia WHERE eia.customerServiceRequest.customerServiceRequestId=?1")
	Optional<ExcludeInternetAccount> getFormFromCSR(int customerServiceRequestId);
	
}
