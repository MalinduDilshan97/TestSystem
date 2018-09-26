package com.spring.starter.service;

import org.springframework.http.ResponseEntity;

import com.spring.starter.model.AtmOrDebit;

public interface AtmOrDebitCardService {
	
	public ResponseEntity<?> atmOrDebitCardRequest(AtmOrDebit atmOrDebit, int customerServiceRequestId);

}
