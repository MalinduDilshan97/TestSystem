package com.spring.starter.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.spring.starter.model.AtmOrDebit;
import com.spring.starter.service.AtmOrDebitCardService;

@RestController
@RequestMapping("/serviceRequest/addAtmOrDebitRequest")
public class AtmOrDebitController {

	@Autowired
	AtmOrDebitCardService atmOrDebitCardService;
	
	@PostMapping
	public ResponseEntity<?> addAtmOrDebitRequest(@RequestBody AtmOrDebit atmOrDebit,@RequestParam(name="requestId") int requestId)
	{
		return atmOrDebitCardService.atmOrDebitCardRequest(atmOrDebit, requestId);
	}
	
	@PutMapping
	public ResponseEntity<?> updateAtmOrDebitRequest(@RequestBody AtmOrDebit atmOrDebit, @RequestParam(name="requestId") int requestId){
		return addAtmOrDebitRequest(atmOrDebit, requestId);
	}
	
}
