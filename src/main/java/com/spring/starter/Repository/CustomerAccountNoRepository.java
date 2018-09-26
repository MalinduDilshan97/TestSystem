package com.spring.starter.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.starter.model.Customer;
import com.spring.starter.model.CustomerAccountNo;

public interface CustomerAccountNoRepository extends JpaRepository<CustomerAccountNo, Integer>{

}
