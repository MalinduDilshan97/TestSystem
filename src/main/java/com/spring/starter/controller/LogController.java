package com.spring.starter.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.starter.service.LogService;

@RestController
@RequestMapping("/logs")
public class LogController {

	@Autowired
	LogService logService;
	
	@GetMapping("/syatemUserRegistrationLogs")
	public ResponseEntity<?> viewAllSyatemUserRegistrationLogs()
	{
		return logService.systemUserAccountCreationLog();
	}
	
	@GetMapping("/systemuserDeactivatelogs")
	public ResponseEntity<?> viewAllSystemuserDeactivatelogs()
	{
		return logService.systemUserDeactivationLog();
	}
	
	@GetMapping("/systemUserActivitiesLogs")
	public ResponseEntity<?> viewAllUserActivitiesLog(){
		return logService.systemUserActivitiesLog();
	}
	
/*	@GetMapping("/systemUserActivationLogs")
	public ResponseEntity<?> viewAllUserActivitivationLog(){
		return logService.systemUserActivationLog();
	}*/
	 
	
}
