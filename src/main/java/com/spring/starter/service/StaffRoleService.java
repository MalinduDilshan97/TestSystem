package com.spring.starter.service;

import org.springframework.http.ResponseEntity;

import com.spring.starter.model.StaffRole;

public interface StaffRoleService {

	public ResponseEntity<?> saveNewUserRole(StaffRole staffRole);
	
	public ResponseEntity<?> getAllUserRoles();
	
	
}
