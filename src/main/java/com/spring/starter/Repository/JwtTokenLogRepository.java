package com.spring.starter.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.starter.model.JwtTokenLog;

public interface JwtTokenLogRepository extends JpaRepository<JwtTokenLog, Integer> {

}
