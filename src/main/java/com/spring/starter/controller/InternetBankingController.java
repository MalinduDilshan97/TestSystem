package com.spring.starter.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.spring.starter.DTO.LinkAccountDTO;
import com.spring.starter.DTO.ReissueLoginPasswordDTO;
import com.spring.starter.model.InternetBanking;
import com.spring.starter.service.InternetBankingService;

@RestController
@RequestMapping("/serviceRequest/internet-banking")
public class InternetBankingController {
	
	@Autowired
	private InternetBankingService internetBankingService;
	
	@PostMapping("/reissue-password")
	public ResponseEntity<?> reissuePassword(@RequestBody ReissueLoginPasswordDTO loginPasswordModel,@RequestParam(name="requestId") int requestId){
		//return new ResponseEntity<>(loginPasswordModel,HttpStatus.OK);
		return internetBankingService.reissuePasswordService(loginPasswordModel, requestId);
	}
	
	@PostMapping("/link-JointAccounts")
	public ResponseEntity<?> linkJointAccounts(@RequestBody LinkAccountDTO accountDTO,@RequestParam(name="requestId") int requestId){
		return internetBankingService.linkJointAccounts(accountDTO, requestId);
	}
	
	@PostMapping("/exclude-accounts")
	public ResponseEntity<?> excludeAccountNo(@RequestBody LinkAccountDTO accountDTO,@RequestParam(name="requestId") int requestId){
		return internetBankingService.excludeAccountNo(accountDTO, requestId);
	}
	
	@PostMapping("/other-service")
	public ResponseEntity<?> otherServices(@RequestBody InternetBanking internetBanking,@RequestParam(name="requestId") int requestId){
		return internetBankingService.internetOtherService(internetBanking, requestId);
	}
	
	@GetMapping
	public ResponseEntity<?> test(){
	
		InternetBanking banking = new InternetBanking();
		banking.setInternetBankingUserId("lakithMuthugala");
		banking.setInactiveUser(true);
		banking.setActiveUser(true);
		
		return new ResponseEntity<>(banking,HttpStatus.OK);
	}
}
