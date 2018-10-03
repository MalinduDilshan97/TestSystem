package com.spring.starter.service;

import org.springframework.http.ResponseEntity;

import com.spring.starter.DTO.LinkAccountDTO;
import com.spring.starter.DTO.ReissueLoginPasswordDTO;
import com.spring.starter.model.InternetBanking;

public interface InternetBankingService {

/*	public ResponseEntity<?> addInternetBankingRequest(InternetBankingDTO internetBankingDto, int customerServiceRequestId);
	
	public ResponseEntity<?> updateInternetBankingRequest(InternetBankingDTO internetBankingDto, int customerServiceRequestId);*/

    public ResponseEntity<?> reissuePasswordService(ReissueLoginPasswordDTO loginPasswordModel, int customerServiceRequestId);

    public ResponseEntity<?> linkJointAccounts(LinkAccountDTO accountDTO, int customerServiceRequestId);

    public ResponseEntity<?> excludeAccountNo(LinkAccountDTO accountDTO, int customerServiceRequestId);

    public ResponseEntity<?> internetOtherService(InternetBanking banking, int customerServiceRequestId);

}
