package com.spring.starter.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.starter.model.StaffRole;

public interface StaffRoleRepository extends JpaRepository<StaffRole, Integer> {

}
