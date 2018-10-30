package com.spring.starter.service;


import java.security.Principal;

import javax.servlet.http.HttpServletRequest;

import com.spring.starter.DTO.ChannelCreateDTO;
import org.springframework.http.ResponseEntity;
import com.spring.starter.DTO.LoginDTO;
import com.spring.starter.DTO.StaffUserDTO;
import com.spring.starter.model.StaffUser;

public interface StaffUserService {

	
	public ResponseEntity<?> getAllStaffUsers();

	public ResponseEntity<?> saveStaffUser(StaffUserDTO staffUserDTO,HttpServletRequest request,Principal principal);
	
	public ResponseEntity<?> staffUserLogin(LoginDTO loginDTO,HttpServletRequest request);
	
	public ResponseEntity<?> makeUserActive(int staffId, Principal principal,HttpServletRequest request);
	
	public ResponseEntity<?> makeUserDeactivate(int staffId, Principal principal,HttpServletRequest request);
	
	public StaffUser getUser();
	
	public ResponseEntity<?> getAllActiveUsers(Principal principal,HttpServletRequest request);
	
	public ResponseEntity<?> getAllDeactivateUsers(Principal principal, HttpServletRequest request);
	
	public ResponseEntity<?> getAllUsers(Principal principal, HttpServletRequest request);
	
	public ResponseEntity<?> saveStaffUserFirstTime(StaffUserDTO staffUserDTO);

	public ResponseEntity<?> saveChannelData(ChannelCreateDTO channelCreateDTO);
	
}
