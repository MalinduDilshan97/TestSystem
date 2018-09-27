package com.spring.starter.service.impl;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.spring.starter.Repository.ChangePermanentMailRepository;
import com.spring.starter.Repository.CustomerServiceRequestRepository;
import com.spring.starter.model.ChangePermanentMail;
import com.spring.starter.model.CustomerServiceRequest;
import com.spring.starter.model.ResponseModel;
import com.spring.starter.service.ChangePermenentMailService;


@Transactional
@Service
public class ChangePermenentMailServiceImpl implements ChangePermenentMailService {

	@Autowired
	private CustomerServiceRequestRepository customerServiceRequestRepository; 
	
	@Autowired
	private ChangePermanentMailRepository changePermanentMailRepository; 
	
	@Override
	public ResponseEntity<?> changePermenantAddress(ChangePermanentMail changePermanentMail, int customerServiceRequestId) {
		ResponseModel responsemodel = new ResponseModel();
		Optional<CustomerServiceRequest> customerServiceRequest = customerServiceRequestRepository.findById(customerServiceRequestId);
		if(!customerServiceRequest.isPresent()) {
			responsemodel.setMessage("There is No such service Available");
			responsemodel.setStatus(false);
			return new ResponseEntity<>(responsemodel, HttpStatus.NO_CONTENT);
		}
		int serviceRequestId = customerServiceRequest.get().getServiceRequest().getDigiFormId();
		System.out.println(serviceRequestId);
		if(serviceRequestId != 3) 
		{
			responsemodel.setMessage("Invalied Request");
			responsemodel.setStatus(false);
			return new ResponseEntity<>(responsemodel, HttpStatus.BAD_REQUEST);
		}
		Optional<ChangePermanentMail> changePermanentMailOpt = changePermanentMailRepository.getFormFromCSR(customerServiceRequestId);
		if(changePermanentMailOpt.isPresent()) {
			System.out.println("enterd to updating");
			changePermanentMail.setChangePermanentMailId(changePermanentMailOpt.get().getChangePermanentMailId());
		}
		changePermanentMail.setCustomerServiceRequest(customerServiceRequest.get());
		System.out.println(changePermanentMail.getCustomerServiceRequest().getCustomerServiceRequestId());
		try {
		changePermanentMailRepository.save(changePermanentMail);
		responsemodel.setMessage("Request saved successfully");
		responsemodel.setStatus(true);
		return new ResponseEntity<>(responsemodel, HttpStatus.CREATED);
		} catch (Exception e) {
			responsemodel.setMessage("Something Went Wrong With The Database Connection.");
			responsemodel.setStatus(true);
			return new ResponseEntity<>(responsemodel, HttpStatus.SERVICE_UNAVAILABLE);
		}
	}
	
}
