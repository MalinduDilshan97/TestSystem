package com.spring.starter.Repository;

import com.spring.starter.model.ServiceRequestCustomerLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceRequestCustomerLogRepository extends JpaRepository<ServiceRequestCustomerLog, Integer> {
}
