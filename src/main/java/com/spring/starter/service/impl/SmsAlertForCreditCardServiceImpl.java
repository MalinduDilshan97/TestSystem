package com.spring.starter.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import com.spring.starter.configuration.ServiceRequestIdConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.spring.starter.DTO.SMSAlertsForCreditCardDTO;
import com.spring.starter.Repository.CustomerServiceRequestRepository;
import com.spring.starter.Repository.SMSAlertsForCreditCardRepository;
import com.spring.starter.Repository.SmsAlertsCreditCardNumbersRepository;
import com.spring.starter.model.CustomerServiceRequest;
import com.spring.starter.model.ResponseModel;
import com.spring.starter.model.SMSAlertsForCreditCard;
import com.spring.starter.model.SmsAlertsCreditCardNumbers;
import com.spring.starter.service.SmsAlertForCreditCardService;

@Service
@Transactional
public class SmsAlertForCreditCardServiceImpl implements SmsAlertForCreditCardService{
	
	@Autowired
	private CustomerServiceRequestRepository customerServiceRequestRepository; 
	
	@Autowired
	private SmsAlertsCreditCardNumbersRepository smsAlertsCreditCardNumbersRepository;
	
	@Autowired
	private SMSAlertsForCreditCardRepository AlertsForCreditCardRepository; 
		
	@Override
	public ResponseEntity<?> smsAlertForCreditCardRequest(SMSAlertsForCreditCardDTO smsAlertsForCreditCardDTO ,int customerServiceRequestId)
	{
		ResponseModel responsemodel = new ResponseModel();
		Optional<CustomerServiceRequest> customerServiceRequest = customerServiceRequestRepository.findById(customerServiceRequestId);
		if(!customerServiceRequest.isPresent()) {
			responsemodel.setMessage("There is No such service Available");
			responsemodel.setStatus(false);
			return new ResponseEntity<>(responsemodel, HttpStatus.NO_CONTENT);
		}
		int serviceRequestId = customerServiceRequest.get().getServiceRequest().getDigiFormId();
		if(serviceRequestId != ServiceRequestIdConfig.SUBSCRIBE_TO_SMS_ALERT_CREDIT_CARD)
		{
			responsemodel.setMessage("Invalied Request");
			responsemodel.setStatus(false);
			return new ResponseEntity<>(responsemodel, HttpStatus.BAD_REQUEST);
		}
			
		List<String> creditCardNumbers = smsAlertsForCreditCardDTO.getCreditCardNumbers();
		List<SmsAlertsCreditCardNumbers> smsAlertsCreditCardNumbers = new ArrayList<>();
		for(String s : creditCardNumbers) 
		{
			SmsAlertsCreditCardNumbers numbers = new SmsAlertsCreditCardNumbers();
			numbers.setCreditCardNumber(s);
			numbers = smsAlertsCreditCardNumbersRepository.save(numbers);
			smsAlertsCreditCardNumbers.add(numbers);
		}
		SMSAlertsForCreditCard smsAlertsForCreditCard = new SMSAlertsForCreditCard();
		
		Optional<SMSAlertsForCreditCard> smsAlertForCreditCardOpt = AlertsForCreditCardRepository.getFormFromCSR(customerServiceRequestId);
		if(smsAlertForCreditCardOpt.isPresent()) {
			smsAlertsForCreditCard.setSMSAlertsForCreditCardId(smsAlertForCreditCardOpt.get().getSMSAlertsForCreditCardId());
		}
		smsAlertsForCreditCard.setCustomerServiceRequest(customerServiceRequest.get());
		smsAlertsForCreditCard.setMobileNumber(smsAlertsForCreditCardDTO.getMobileNumber());
		smsAlertsForCreditCard.setSMSAlertsForCreditCard(smsAlertsCreditCardNumbers);
		try {
			AlertsForCreditCardRepository.save(smsAlertsForCreditCard);
			responsemodel.setMessage("Request saved successfully");
			responsemodel.setStatus(true);
			return new ResponseEntity<>(responsemodel, HttpStatus.CREATED);
		} catch (Exception e) {
			responsemodel.setMessage("Something Went Wrong With The Connection");
			responsemodel.setStatus(true);
			return new ResponseEntity<>(responsemodel, HttpStatus.SERVICE_UNAVAILABLE);
		}		
	}
}
