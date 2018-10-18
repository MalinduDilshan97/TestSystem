package com.spring.starter.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.spring.starter.DTO.LinkAccountDTO;
import com.spring.starter.DTO.ReissueLoginPasswordDTO;
import com.spring.starter.model.InternetBanking;
import com.spring.starter.service.InternetBankingService;

import javax.validation.Valid;

@RestController
@RequestMapping("/serviceRequest/internet-banking")
public class InternetBankingController {
	
	@Autowired
	private InternetBankingService internetBankingService;
	
	@PostMapping("/reissue-password")
	public ResponseEntity<?> reissuePassword(@RequestBody @Valid ReissueLoginPasswordDTO loginPasswordModel,@RequestParam(name="requestId") int requestId){
		//return new ResponseEntity<>(loginPasswordModel,HttpStatus.OK);
		return internetBankingService.reissuePasswordService(loginPasswordModel, requestId);
	}

	@PutMapping("/reissue-password")
	public ResponseEntity<?> updateReissuePassword(@RequestBody @Valid ReissueLoginPasswordDTO loginPasswordModel,@RequestParam(name="requestId") int requestId){
		//return new ResponseEntity<>(loginPasswordModel,HttpStatus.OK);
		return internetBankingService.reissuePasswordService(loginPasswordModel, requestId);
	}
	
	@PostMapping("/link-JointAccounts")
	public ResponseEntity<?> linkJointAccounts(@RequestBody @Valid LinkAccountDTO accountDTO,@RequestParam(name="requestId") int requestId){
		return internetBankingService.linkJointAccounts(accountDTO, requestId);
	}

	@PutMapping("/link-JointAccounts")
	public ResponseEntity<?> updateLinkJointAccounts(@RequestBody @Valid LinkAccountDTO accountDTO,@RequestParam(name="requestId") int requestId){
		return internetBankingService.linkJointAccounts(accountDTO, requestId);
	}
	
	@PostMapping("/exclude-accounts")
	public ResponseEntity<?> excludeAccountNo(@RequestBody @Valid LinkAccountDTO accountDTO,@RequestParam(name="requestId") int requestId){
		return internetBankingService.excludeAccountNo(accountDTO, requestId);
	}

	@PutMapping("/exclude-accounts")
	public ResponseEntity<?> updateexcludeAccountNo(@RequestBody @Valid LinkAccountDTO accountDTO,@RequestParam(name="requestId") int requestId){
		return internetBankingService.excludeAccountNo(accountDTO, requestId);
	}
	
	@PostMapping("/other-service")
	public ResponseEntity<?> otherServices(@RequestBody @Valid InternetBanking internetBanking, @RequestParam(name="requestId") int requestId){
		//return new ResponseEntity<>(internetBanking,HttpStatus.OK);

		return internetBankingService.internetOtherService(internetBanking, requestId);
	}

	@PutMapping("/other-service")
	public ResponseEntity<?> updateServices(@RequestBody @Valid InternetBanking internetBanking, @RequestParam(name="requestId") int requestId){
		//return new ResponseEntity<>(internetBanking,HttpStatus.OK);
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
