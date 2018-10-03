package com.spring.starter.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.spring.starter.DTO.MailingMailDTO;
import com.spring.starter.service.MailingMailService;

@RestController
@RequestMapping("serviceRequest/address/change-permenenet")
@CrossOrigin
public class MailingMailController {

	@Autowired
	private MailingMailService mailingMailService;
	
	@PostMapping
	public ResponseEntity<?> addNewRequest(@RequestBody MailingMailDTO mailingMailDTO, @RequestParam(name="requestId") int requestId)
	{
		return mailingMailService.addchangeMailingMailRequest(mailingMailDTO, requestId);
	}
	
	@PutMapping
	public ResponseEntity<?> updateNewRequest(@RequestBody MailingMailDTO mailingMailDTO, @RequestParam(name="requestId") int requestId)
	{
		return mailingMailService.addchangeMailingMailRequest(mailingMailDTO, requestId);
	}
	
	@GetMapping
	public ResponseEntity<?> test()
	{
		List<String> accountNos = new ArrayList<String>();
		accountNos.add("12313232123");
		accountNos.add("12312323232");
		
		MailingMailDTO mailingMailDTO= new MailingMailDTO();
		mailingMailDTO.setNewMailingAddress("thilakavilla,Thuttiripitiya,Halthota,Bandaragama");
		mailingMailDTO.setCity("bandaragama");
		mailingMailDTO.setPostalCode("13213");
		mailingMailDTO.setStateOrProvince("Western");
		mailingMailDTO.setMailingMailAccountNo(accountNos);
		
		return new ResponseEntity<>(mailingMailDTO,HttpStatus.OK);
	}
}
