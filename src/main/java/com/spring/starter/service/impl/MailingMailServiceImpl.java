package com.spring.starter.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.spring.starter.DTO.MailingMailDTO;
import com.spring.starter.Repository.CustomerServiceRequestRepository;
import com.spring.starter.model.CustomerServiceRequest;
import com.spring.starter.model.ResponseModel;
import com.spring.starter.service.MailingMailService;

public class MailingMailServiceImpl implements MailingMailService {

	@Autowired
	private CustomerServiceRequestRepository customerServiceRequestRepository; 
	
	@Override
	public ResponseEntity<?> addchangeMailingMailRequest(MailingMailDTO mailingMailDTO, int customerServiceRequestId) {
		
		ResponseModel responsemodel = new ResponseModel();
		Optional<CustomerServiceRequest> customerServiceRequest = customerServiceRequestRepository.findById(customerServiceRequestId);
		if(!customerServiceRequest.isPresent()) {
			responsemodel.setMessage("There is No such service Available");
			responsemodel.setStatus(false);
			return new ResponseEntity<>(responsemodel, HttpStatus.NO_CONTENT);
		}
		int serviceRequestId = customerServiceRequest.get().getServiceRequest().getDigiFormId();
		if(serviceRequestId != 5) 
		{
			responsemodel.setMessage("Invalied Request");
			responsemodel.setStatus(false);
			return new ResponseEntity<>(responsemodel, HttpStatus.BAD_REQUEST);
		}
		

		return null;
	}

}
