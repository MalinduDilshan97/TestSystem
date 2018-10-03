package com.spring.starter.Repository;

import com.spring.starter.model.ServiceRequestFormLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceRequestFormLogRepository extends JpaRepository<ServiceRequestFormLog, Integer> {
}
