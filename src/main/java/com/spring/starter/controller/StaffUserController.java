package com.spring.starter.controller;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.spring.starter.DTO.LoginDTO;
import com.spring.starter.DTO.StaffUserDTO;
import com.spring.starter.configuration.JwtAuthenticationConfig;
import com.spring.starter.model.StaffUser;
import com.spring.starter.service.StaffUserService;

@RestController
@RequestMapping("/staffUsers")
@CrossOrigin
public class StaffUserController {

    @Autowired
    private JwtAuthenticationConfig config;
	
	@Autowired
	StaffUserService staffUserService; 
	
	@PostMapping(produces=MediaType.APPLICATION_JSON_VALUE,path="/addNewUser")
	public ResponseEntity<?> saveNewStaffUser(@RequestBody StaffUserDTO staffUserDTO, Principal principal){
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		return staffUserService.saveStaffUser(staffUserDTO,request,principal);
	}
	
	@GetMapping
	public ResponseEntity<?> getAllStaffUser(){
		return staffUserService.getAllStaffUsers();
	}
	
	@PostMapping(produces=MediaType.APPLICATION_JSON_VALUE,path="/login")
	public ResponseEntity<?> StaffUserLogin(@RequestBody LoginDTO loginDTO)
	{
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		return staffUserService.staffUserLogin(loginDTO,request);
	}
	
	@GetMapping(produces=MediaType.APPLICATION_JSON_VALUE,path="/activete/{staffId}")
	public ResponseEntity<?> setAnUserToActive(@PathVariable int staffId,Principal principal){
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		return staffUserService.makeUserActive(staffId,principal,request);
	}
	
	@GetMapping(produces=MediaType.APPLICATION_JSON_VALUE,path="/deactive/{staffId}")
	public ResponseEntity<?> setAnUserToDeactive(@PathVariable int staffId,Principal principal){
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		return staffUserService.makeUserDeactivate(staffId,principal,request);
	}

	@GetMapping("/token")
	public String getToken() 
	{
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		String token = request.getHeader(config.getHeader());
		token = token.replace(config.getPrefix() + " ", "");
		return token;
	}
	
	
	@GetMapping("/test")
	public String getTest() 
	{
		 HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		 return request.getRemoteAddr();
	}
	
	@GetMapping("/getActiveUsers")
	public ResponseEntity<?> getAllActiveUsers(Principal principal) 
	{
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		return staffUserService.getAllActiveUsers(principal, request);
	}
	
	@GetMapping("/getDeactiveUsers")
	public ResponseEntity<?> getAllDeactiveUsers(Principal principal) 
	{
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		return staffUserService.getAllDeactivateUsers(principal, request);
	}
	
	@PostMapping("/addStaffUserFirstTime")
	public ResponseEntity<?> saveStaffUserFirstTime(@RequestBody StaffUserDTO staffUserDTO)
	{
		return staffUserService.saveStaffUserFirstTime(staffUserDTO);
	}	
}
