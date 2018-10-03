package com.spring.starter.service;

import org.springframework.http.ResponseEntity;

public interface LogService {

	public ResponseEntity<?> systemUserAccountCreationLog();
	
	public ResponseEntity<?> systemUserDeactivationLog();
	
	public ResponseEntity<?> systemUserActivitiesLog();
	
	public ResponseEntity<?> systemUserActivationLog();
}
