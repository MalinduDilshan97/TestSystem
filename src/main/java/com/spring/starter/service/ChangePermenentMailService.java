package com.spring.starter.service;

import org.springframework.http.ResponseEntity;

import com.spring.starter.model.ChangePermanentMail;

public interface ChangePermenentMailService {

	public ResponseEntity<?> changePermenantAddress(ChangePermanentMail changePermanentMail, int customerServiceRequestId);
}
