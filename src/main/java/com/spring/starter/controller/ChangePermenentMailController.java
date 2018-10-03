package com.spring.starter.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.spring.starter.model.ChangePermanentMail;
import com.spring.starter.service.ChangePermenentMailService;

@RestController
@RequestMapping("/serviceRequest/changePermanentAddress")
public class ChangePermenentMailController {

	@Autowired
	ChangePermenentMailService changePermenentMailService;
	
	@PostMapping
	public ResponseEntity<?> changePermenentMailRequest(@RequestBody @Valid ChangePermanentMail changePermanentMail, @RequestParam(name="requestId") int requestId)
	{
		return changePermenentMailService.changePermenantAddress(changePermanentMail, requestId);
	}
	
	@PutMapping
	public ResponseEntity<?> updatePermenentMailRequest(@RequestBody @Valid ChangePermanentMail changePermanentMail, @RequestParam(name="requestId") int requestId)
	{
		return changePermenentMailService.changePermenantAddress(changePermanentMail, requestId);
	}
	
}
