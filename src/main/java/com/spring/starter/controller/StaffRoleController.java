package com.spring.starter.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.starter.model.StaffRole;
import com.spring.starter.service.impl.StaffRoleServiceImpl;

@RestController
@RequestMapping("/staffRoles")
@CrossOrigin
public class StaffRoleController {

	private static final Logger logger = LoggerFactory.getLogger(StaffUserController.class);
	
	@Autowired
	private StaffRoleServiceImpl staffRoleService; 
	
	@PostMapping
	public ResponseEntity<?> saveNewUserRole(@RequestBody StaffRole staffRole) {
		//logger.info("request - methodName | (URL - api-url) | uuid = {}, userUUID = {}");
		return	staffRoleService.saveNewUserRole(staffRole);
	}
	
	@GetMapping
	public ResponseEntity<?> getAllUserRoles(){
		return staffRoleService.getAllUserRoles();
	}
	
}
