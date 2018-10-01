package com.spring.starter.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.starter.model.ContactDetails;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ContactDetailsRepository extends JpaRepository<ContactDetails, Integer> {

    @Query("SELECT cd FROM ContactDetails cd WHERE cd.customerServiceRequest.customerServiceRequestId=?1")
    Optional<ContactDetails> getFormFromCSR(int customerServiceRequestId);


}
