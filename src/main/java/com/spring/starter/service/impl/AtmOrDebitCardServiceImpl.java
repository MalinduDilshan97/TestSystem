package com.spring.starter.service.impl;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.spring.starter.Repository.AtmOrDebitRepository;
import com.spring.starter.Repository.CustomerServiceRequestRepository;
import com.spring.starter.model.AtmOrDebit;
import com.spring.starter.model.CustomerServiceRequest;
import com.spring.starter.model.ResponseModel;
import com.spring.starter.service.AtmOrDebitCardService;

@Service
@Transactional
public class AtmOrDebitCardServiceImpl implements AtmOrDebitCardService {

	@Autowired
	private CustomerServiceRequestRepository customerServiceRequestRepository; 
	
	@Autowired AtmOrDebitRepository atmOrDebitRepository;
	
	@Override
	public ResponseEntity<?> atmOrDebitCardRequest(AtmOrDebit atmOrDebit, int customerServiceRequestId) {
		ResponseModel responsemodel = new ResponseModel();
		Optional<CustomerServiceRequest> customerServiceRequest = customerServiceRequestRepository.findById(customerServiceRequestId);
		if(!customerServiceRequest.isPresent()) {
			responsemodel.setMessage("There is No such service Available");
			responsemodel.setStatus(false);
			return new ResponseEntity<>(responsemodel, HttpStatus.NO_CONTENT);
		}
		int serviceRequestId = customerServiceRequest.get().getServiceRequest().getDigiFormId();
		if(serviceRequestId != 2) 
		{
			responsemodel.setMessage("Invalied Request");
			responsemodel.setStatus(false);
			return new ResponseEntity<>(responsemodel, HttpStatus.BAD_REQUEST);
		}
		
		Optional<AtmOrDebit> optionalAtmOrDebit = atmOrDebitRepository.getFormFromCSR(customerServiceRequestId);
		if(optionalAtmOrDebit.isPresent()) {
			atmOrDebit.setAtmOrDebitId(optionalAtmOrDebit.get().getAtmOrDebitId());
		}
		
		atmOrDebit.setCustomerServiceRequest(customerServiceRequest.get());
		if(atmOrDebit.isSubscribeToSmsAlerts()) 
		{
			if(atmOrDebit.getMobileNumberForSmsAlert() == null) 
			{
				responsemodel.setMessage("Please Complete All your the Data For Complete the request");
				responsemodel.setStatus(false);
				return new ResponseEntity<>(responsemodel, HttpStatus.BAD_REQUEST);
			}
		} if(atmOrDebit.isIncresePointOfSale()) {
			if(atmOrDebit.getIncresingAmmount() == 0) 
			{
				responsemodel.setMessage("Please Complete All your the Data For Complete the request");
				responsemodel.setStatus(false);
				return new ResponseEntity<>(responsemodel, HttpStatus.BAD_REQUEST);
			}
		} if(atmOrDebit.isLinkNewAccountToCard()) {
			if(atmOrDebit.getPrimaryAccNo() == null || atmOrDebit.getSecondaryAccNo()==null || atmOrDebit.getNewPrimaryAccountNo()==null) {
				responsemodel.setMessage("Please Complete All your the Data For Complete the request");
				responsemodel.setStatus(false);
				return new ResponseEntity<>(responsemodel, HttpStatus.BAD_REQUEST);
			}
		} if(atmOrDebit.isReissueAPin()) {
			if(atmOrDebit.isAtBranch()) 
			{
				if(atmOrDebit.getBranch() == null) 
				{
					responsemodel.setMessage("Please Complete All your the Data For Complete the request");
					responsemodel.setStatus(false);
					return new ResponseEntity<>(responsemodel, HttpStatus.BAD_REQUEST);
				} 
			} else if(atmOrDebit.isPostToAddress()) {
				if(atmOrDebit.getPostAddress()==null) {
					responsemodel.setMessage("Please Complete All your the Data For Complete the request");
					responsemodel.setStatus(false);
					return new ResponseEntity<>(responsemodel, HttpStatus.BAD_REQUEST);
				}
			}
		}
		try {
		atmOrDebitRepository.save(atmOrDebit);
		responsemodel.setMessage("atm or debit request saved successfully");
		responsemodel.setStatus(true);
		return new ResponseEntity<>(responsemodel, HttpStatus.CREATED);
		} catch (Exception e) {
			responsemodel.setMessage("Something went wrong with the DB connection");
			responsemodel.setStatus(true);
			return new ResponseEntity<>(responsemodel, HttpStatus.SERVICE_UNAVAILABLE);
		}
	}
	
	public ResponseEntity<?> updateAtmOrDebitCardRequest(AtmOrDebit request ,int FormId){
		ResponseModel responsemodel = new ResponseModel();
	 	Optional<AtmOrDebit> atmOrDebitOpt = atmOrDebitRepository.findById(FormId);
	 	if(!atmOrDebitOpt.isPresent()) {
			responsemodel.setMessage("Invalied Request");
			responsemodel.setStatus(false);
			return new ResponseEntity<>(responsemodel, HttpStatus.BAD_REQUEST);
	 	}
	 	request.setAtmOrDebitId(atmOrDebitOpt.get().getAtmOrDebitId());
		if(request.isSubscribeToSmsAlerts()) 
		{
			if(request.getMobileNumberForSmsAlert() == null) 
			{
				responsemodel.setMessage("Please Complete All your the Data For Complete the request");
				responsemodel.setStatus(false);
				return new ResponseEntity<>(responsemodel, HttpStatus.BAD_REQUEST);
			}
		} if(request.isIncresePointOfSale()) {
			if(request.getIncresingAmmount() == 0) 
			{
				responsemodel.setMessage("Please Complete All your the Data For Complete the request");
				responsemodel.setStatus(false);
				return new ResponseEntity<>(responsemodel, HttpStatus.BAD_REQUEST);
			}
		} if(request.isLinkNewAccountToCard()) {
			if(request.getPrimaryAccNo() == null || request.getSecondaryAccNo()==null || request.getNewPrimaryAccountNo()==null) {
				responsemodel.setMessage("Please Complete All your the Data For Complete the request");
				responsemodel.setStatus(false);
				return new ResponseEntity<>(responsemodel, HttpStatus.BAD_REQUEST);
			}
		} if(request.isReissueAPin()) {
			if(request.isAtBranch()) 
			{
				if(request.getBranch() == null) 
				{
					responsemodel.setMessage("Please Complete All your the Data For Complete the request");
					responsemodel.setStatus(false);
					return new ResponseEntity<>(responsemodel, HttpStatus.BAD_REQUEST);
				} 
			} else if(request.isPostToAddress()) {
				if(request.getPostAddress()==null) {
					responsemodel.setMessage("Please Complete All your the Data For Complete the request");
					responsemodel.setStatus(false);
					return new ResponseEntity<>(responsemodel, HttpStatus.BAD_REQUEST);
				}
			}
		}
		
		try {
			atmOrDebitRepository.save(request);
			responsemodel.setMessage("atm or debit request saved successfully");
			responsemodel.setStatus(true);
			return new ResponseEntity<>(responsemodel, HttpStatus.CREATED);
			} catch (Exception e) {
				responsemodel.setMessage("Something went wrong with the DB connection");
				responsemodel.setStatus(true);
				return new ResponseEntity<>(responsemodel, HttpStatus.SERVICE_UNAVAILABLE);
			}
	}
}
