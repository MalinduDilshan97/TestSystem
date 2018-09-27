package com.spring.starter.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.starter.model.DeactivateLog;

public interface DeactivateLogRepository extends JpaRepository<DeactivateLog, Integer> {

}
