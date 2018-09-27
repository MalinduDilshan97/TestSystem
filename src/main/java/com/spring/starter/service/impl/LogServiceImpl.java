package com.spring.starter.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.swing.text.html.FormView;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.spring.starter.DTO.viewUsersLogDTO;
import com.spring.starter.Repository.DeactivateLogRepository;
import com.spring.starter.Repository.SetActiveLogRepository;
import com.spring.starter.Repository.StaffUserRepository;
import com.spring.starter.Repository.StaffUserSaveLogRepository;
import com.spring.starter.Repository.ViewUsersLogRepository;
import com.spring.starter.model.DeactivateLog;
import com.spring.starter.model.ResponseModel;
import com.spring.starter.model.SetActiveLog;
import com.spring.starter.model.StaffUser;
import com.spring.starter.model.StaffUserSaveLog;
import com.spring.starter.model.ViewUsersLog;
import com.spring.starter.service.LogService;

@Service
@Transactional
public class LogServiceImpl implements LogService {

    @Autowired
    private StaffUserSaveLogRepository staffUserSaveLogRepository;
    
	@Autowired
	private DeactivateLogRepository deactivateLogRepository;
	
    @Autowired
    private ViewUsersLogRepository viewUsersLogRepository; 
    
	@Autowired
	private StaffUserRepository staffUserRepository;
	
	@Autowired
	private SetActiveLogRepository activeLogRepository;
	
	@Override
	public ResponseEntity<?> systemUserAccountCreationLog() {
		 
		List<StaffUserSaveLog> staffUserSaveLogs = staffUserSaveLogRepository.findAll();
		if(staffUserSaveLogs.isEmpty()) 
		{
			return printNoContentLog();
		}
		return new ResponseEntity<>(staffUserSaveLogs,HttpStatus.OK);
	}

	@Override
	public ResponseEntity<?> systemUserDeactivationLog() {
		List<DeactivateLog> deactivateLogs = deactivateLogRepository.findAll();
		if(deactivateLogs.isEmpty()) 
		{
			return printNoContentLog();
		}
		return new ResponseEntity<>(deactivateLogs,HttpStatus.OK);
		
	}

	@Override
	public ResponseEntity<?> systemUserActivitiesLog() {
		List<ViewUsersLog> viewUsersLogs = viewUsersLogRepository.findAll();
		List<viewUsersLogDTO> viewUsersLogDTOs = new ArrayList<viewUsersLogDTO>();
		
		if(viewUsersLogs.isEmpty()) 
		{
			return printNoContentLog();
		}
		for(ViewUsersLog v : viewUsersLogs) 
		{
			viewUsersLogDTO logDTO = new viewUsersLogDTO();
			logDTO.setViewedIp(v.getViewedIp());
			logDTO.setViewedItem(v.getViewedItem());
			logDTO.setViewedTime(v.getViwedTime());
			Optional<StaffUser> staffUserOpt = staffUserRepository.findById(v.getViewUsersLogId());
			if(!staffUserOpt.isPresent()) 
			{
				ResponseModel responseModel = new ResponseModel();
				responseModel.setMessage("Some One Has Deleted Data On The Database By Accessing The Database. Deleted ID is "+v.getViewUsersLogId());
				responseModel.setStatus(false);
				return new ResponseEntity<>(responseModel,HttpStatus.UNAVAILABLE_FOR_LEGAL_REASONS);
			}
			else 
			{
				logDTO.setViewUser(staffUserOpt.get());
			}
			viewUsersLogDTOs.add(logDTO);
		}
		
		return new ResponseEntity<>(viewUsersLogDTOs,HttpStatus.OK);
	}
	
	private ResponseEntity<ResponseModel> printNoContentLog() {
		ResponseModel responseModel = new ResponseModel();
		responseModel.setMessage("There is no logs in the database");
		responseModel.setStatus(false);
		return new ResponseEntity<>(responseModel,HttpStatus.NO_CONTENT);
	}

	@Override
	public ResponseEntity<?> systemUserActivationLog() {
		List<SetActiveLog> activetionLogs = activeLogRepository.findAll();
		if(activetionLogs.isEmpty()) 
		{
			return printNoContentLog();
		}
		return new ResponseEntity<>(activetionLogs,HttpStatus.OK);
	}
}
