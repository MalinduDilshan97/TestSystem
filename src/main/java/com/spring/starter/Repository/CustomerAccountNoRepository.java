package com.spring.starter.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.starter.model.Customer;
import com.spring.starter.model.CustomerAccountNo;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CustomerAccountNoRepository extends JpaRepository<CustomerAccountNo, Integer>{

    @Query("SELECT ca from CustomerAccountNo ca WHERE ca.accountNumber= :accountNumber")
    public Optional<CustomerAccountNo> findByAccountNumber(@Param("accountNumber") String accountNumber);



}
