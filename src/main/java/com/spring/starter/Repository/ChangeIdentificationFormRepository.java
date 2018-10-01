package com.spring.starter.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.starter.model.IdentificationForm;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ChangeIdentificationFormRepository extends JpaRepository<IdentificationForm, Integer> {

    @Query("SELECT if FROM IdentificationForm if WHERE if.customerServiceRequest.customerServiceRequestId=?1")
    Optional<IdentificationForm> getFormFromCSR(int customerServiceRequestId);

}
