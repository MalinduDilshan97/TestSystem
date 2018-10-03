package com.spring.starter.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.starter.model.StaffUserSaveLog;

public interface StaffUserSaveLogRepository extends JpaRepository<StaffUserSaveLog, Integer> {

}
