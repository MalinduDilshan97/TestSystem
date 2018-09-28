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

import com.spring.starter.DTO.LinkAccountDTO;
import com.spring.starter.DTO.ReissueLoginPasswordDTO;
import com.spring.starter.Exception.CustomException;
import com.spring.starter.Repository.CustomerServiceRequestRepository;
import com.spring.starter.Repository.ExcludeInternetAccountRepository;
import com.spring.starter.Repository.InternetBankingExcludeAccountNumbersRepository;
import com.spring.starter.Repository.InternetBankingLinkAccountNumbersRepository;
import com.spring.starter.Repository.InternetBankingRepository;
import com.spring.starter.Repository.LinkAccountModelRepository;
import com.spring.starter.Repository.NDBBranchRepository;
import com.spring.starter.Repository.ReissueLoginPasswordModelRepository;
import com.spring.starter.model.CustomerServiceRequest;
import com.spring.starter.model.ExcludeInternetAccount;
import com.spring.starter.model.InternetBanking;
import com.spring.starter.model.InternetBankingExcludeAccountNumbers;
import com.spring.starter.model.InternetBankingLinkAccountNumbers;
import com.spring.starter.model.LinkAccountModel;
import com.spring.starter.model.NDBBranch;
import com.spring.starter.model.ReissueLoginPasswordModel;
import com.spring.starter.model.ResponseModel;
import com.spring.starter.service.InternetBankingService;

@Service
@Transactional
public class InternetBankingImpl implements InternetBankingService{
	
	@Autowired
	private CustomerServiceRequestRepository customerServiceRequestRepository;
	
	@Autowired
	private InternetBankingRepository internetBankingRepository;
	
	@Autowired
	private InternetBankingExcludeAccountNumbersRepository excludeAccountNumbersRepository;
	
	@Autowired
	private ExcludeInternetAccountRepository excludeInternetAccountRepository; 
	
	@Autowired
	private InternetBankingLinkAccountNumbersRepository linkAccountNumbersRepository; 
	
	@Autowired
	private NDBBranchRepository branchRepository; 
	
	@Autowired
	private ReissueLoginPasswordModelRepository loginPasswordModelRepository;
	
	@Autowired
	private  LinkAccountModelRepository linkAccountModelRepository; 
	
	
/*	@Override
	public ResponseEntity<?> addInternetBankingRequest(InternetBankingDTO internetBankingDto,
			int customerServiceRequestId) {
		ResponseModel responsemodel = new ResponseModel();
		Optional<CustomerServiceRequest> customerServiceRequest = customerServiceRequestRepository.findById(customerServiceRequestId);
		if(!customerServiceRequest.isPresent()) {
			responsemodel.setMessage("There is No such service Available");
			responsemodel.setStatus(false);
			return new ResponseEntity<>(responsemodel, HttpStatus.NO_CONTENT);
		}
		int serviceRequestId = customerServiceRequest.get().getServiceRequest().getDigiFormId();
		if(serviceRequestId != 7) 
		{
			responsemodel.setMessage("Invalied Request");
			responsemodel.setStatus(false);
			return new ResponseEntity<>(responsemodel, HttpStatus.BAD_REQUEST);
		}
		
		Optional<InternetBanking> internetBankingOpt = internetBankingRepository.getFormFromCSR(customerServiceRequestId);
		InternetBanking internetBanking = new InternetBanking();
		if(internetBankingOpt.isPresent()) {
			internetBanking.setInternetBankingId(internetBankingOpt.get().getInternetBankingId()); 
		}
		
		if(internetBankingDto.isReissueALoginPassword()) 
		{
			if(internetBankingDto.isAtbranch()) 
			{
				if(internetBankingDto.getBranchId() == 0) {
				responsemodel.setMessage("Plese complete branch details before submmiting the form");
				responsemodel.setStatus(false);
				return new ResponseEntity<>(responsemodel, HttpStatus.BAD_REQUEST);
				}
			} else if(internetBankingDto.isPostToAddress()) 
			{
				if(internetBankingDto.getAddress() == null) {
					responsemodel.setMessage("Plese complete Address before submmiting the form");
					responsemodel.setStatus(false);
					return new ResponseEntity<>(responsemodel, HttpStatus.BAD_REQUEST);
				}
			} 
		}
		List<InternetBankingLinkAccountNumbers> linkAccountNumbers = new ArrayList<InternetBankingLinkAccountNumbers>();
		if(internetBankingDto.isLinkjointAccounts()) {
			if(internetBankingDto.getInternetBankingLinkAccountNumbers().isEmpty()) {
				responsemodel.setMessage("Plese complete Account numbers before submmiting the form");
				responsemodel.setStatus(false);
				return new ResponseEntity<>(responsemodel, HttpStatus.BAD_REQUEST);
			} else if (!internetBankingDto.getInternetBankingLinkAccountNumbers().isEmpty()) {
				for(String s :internetBankingDto.getInternetBankingLinkAccountNumbers()) 
				{
					InternetBankingLinkAccountNumbers accountNumber = new InternetBankingLinkAccountNumbers();
					accountNumber.setAccountNumber(s);
					accountNumber = linkAccountNumbersRepository.save(accountNumber);
					linkAccountNumbers.add(accountNumber);
				}
			}
		}
		
		List<InternetBankingExcludeAccountNumbers> excludeAccountNumbers = new ArrayList<>();
		if(internetBankingDto.isExcludeAccounts()) {
			if(internetBankingDto.getBankingExcludeAccountNumbers().isEmpty()) {
				responsemodel.setMessage("Plese complete Account numbers before submmiting the form");
				responsemodel.setStatus(false);
				return new ResponseEntity<>(responsemodel, HttpStatus.BAD_REQUEST);
			} else if (!internetBankingDto.getBankingExcludeAccountNumbers().isEmpty()) {
				for(String s :internetBankingDto.getBankingExcludeAccountNumbers()) 
				{
					InternetBankingExcludeAccountNumbers accountNumber = new InternetBankingExcludeAccountNumbers();
					accountNumber.setAccountNumber(s);
					accountNumber = excludeAccountNumbersRepository.save(accountNumber);
					excludeAccountNumbers.add(accountNumber);
				}
			}
		}
		System.out.println(internetBankingDto.getBranchId());
		Optional<NDBBranch> branch = branchRepository.findById(internetBankingDto.getBranchId());
		if(!branch.isPresent()) {
				responsemodel.setMessage("invalied bank details plese insert correct details and try again");
				responsemodel.setStatus(false);
				return new ResponseEntity<>(responsemodel, HttpStatus.BAD_REQUEST);
		}
		
		try {
			internetBanking.setExistingBankingUserId(internetBankingDto.getExistingBankingUserId());
			internetBanking.setReissueALoginPassword(internetBankingDto.isReissueALoginPassword());
			internetBanking.setAtbranch(internetBankingDto.isAtbranch());
			internetBanking.setBranch(branch.get());
			internetBanking.setCorrespondanceAddress(internetBankingDto.isCorrespondanceAddress());
			internetBanking.setPostToAddress(internetBankingDto.isPostToAddress());
			internetBanking.setLinkjointAccounts(internetBankingDto.isLinkjointAccounts());
			internetBanking.setInternetBankingLinkAccountNumbers(linkAccountNumbers);
			internetBanking.setExcludeAccounts(internetBankingDto.isExcludeAccounts());
			internetBanking.setBankingExcludeAccountNumbers(excludeAccountNumbers);
			internetBanking.setCancelInternetbanking(internetBankingDto.isCancelInternetbanking());
			internetBanking.setActiveUser(internetBanking.isActiveUser());
			internetBanking.setInactiveUser(internetBankingDto.isInactiveUser());
			internetBanking.setCustomerServiceRequest(customerServiceRequest.get());
			internetBanking =internetBankingRepository.save(internetBanking);
			responsemodel.setMessage("Service saved Successfully");
			responsemodel.setStatus(false);
			return new ResponseEntity<>(internetBanking, HttpStatus.CREATED);	
			
		} catch (IndexOutOfBoundsException e) {
			responsemodel.setMessage(e.getMessage());
			responsemodel.setStatus(false);
			return new ResponseEntity<>(responsemodel, HttpStatus.BAD_REQUEST);
		} catch (JpaSystemException e) {
			responsemodel.setMessage(e.getMessage());
			responsemodel.setStatus(false);
			return new ResponseEntity<>(responsemodel, HttpStatus.SERVICE_UNAVAILABLE);
		}catch (Exception e) {
			responsemodel.setMessage(e.getMessage());
			responsemodel.setStatus(false);
			return new ResponseEntity<>(responsemodel, HttpStatus.BAD_REQUEST);
		}
		
	}*/
	
/*	public ResponseEntity<?> reissuePasswordService(ReissueLoginPasswordDTO loginPasswordModel,int customerServiceRequestId){
		ResponseModel responsemodel = new ResponseModel();
		Optional<CustomerServiceRequest> customerServiceRequest = customerServiceRequestRepository.findById(customerServiceRequestId);
		if(!customerServiceRequest.isPresent()) {
			responsemodel.setMessage("There is No such service Available");
			responsemodel.setStatus(false);
			return new ResponseEntity<>(responsemodel, HttpStatus.NO_CONTENT);
		}
		int serviceRequestId = customerServiceRequest.get().getServiceRequest().getDigiFormId();
		if(serviceRequestId != 7) 
		{
			responsemodel.setMessage("Invalied Request");
			responsemodel.setStatus(false);
			return new ResponseEntity<>(responsemodel, HttpStatus.BAD_REQUEST);
		}
		if(loginPasswordModel.isAtBranch()) {
			if(loginPasswordModel.getBranchId()== 0) {
				responsemodel.setMessage("Please insert branch details before submit");
				responsemodel.setStatus(true);
				return new ResponseEntity<> (responsemodel,HttpStatus.BAD_REQUEST);
			}
		}
		Optional<NDBBranch> branchOpt = branchRepository.findById(loginPasswordModel.getBranchId());
		if(!branchOpt.isPresent()) {
			responsemodel.setMessage("Invalied branch details");
			responsemodel.setStatus(true);
			return new ResponseEntity<> (responsemodel,HttpStatus.BAD_REQUEST);
		}
		System.out.println(branchOpt.get().getBranch_name());
		ReissueLoginPasswordModel passwordModel = new ReissueLoginPasswordModel();
		System.out.println(2);

		System.out.println(3);
		if(loginPasswordModel.isPostToAddress()) {
			if(loginPasswordModel.getAddresss() == null) {
				responsemodel.setMessage("Please insert Address before submit");
				responsemodel.setStatus(true);
				return new ResponseEntity<> (responsemodel,HttpStatus.BAD_REQUEST);
			}
		}
		System.out.println(4);
		passwordModel.setBranch(branchOpt.get());
		System.out.println(passwordModel.getBranch());
		passwordModel.setAtBranch(loginPasswordModel.isAtBranch());
		System.out.println(passwordModel.isAtBranch());
		passwordModel.setBankingUserId(loginPasswordModel.getAddresss());
		System.out.println("lakith muthugala");
		passwordModel.setPostToCorrespondenceAddress(loginPasswordModel.isPostToCorrespondenceAddress());
		System.out.println(passwordModel.isPostToCorrespondenceAddress());
		passwordModel.setPostToAddress(loginPasswordModel.isPostToAddress());
		passwordModel.setAddresss(loginPasswordModel.getAddresss());
		
		
		Optional<ReissueLoginPasswordModel> loginPasswordModelOpt = loginPasswordModelRepository.getFormFromCSR(customerServiceRequestId);
		if(loginPasswordModelOpt.isPresent()) {
			System.out.println(5);
			passwordModel.setReissueLoginPasswordModelId(loginPasswordModelOpt.get().getReissueLoginPasswordModelId());
		}
		System.out.println(6);
		try {
			loginPasswordModel.setCustomerServiceRequest(customerServiceRequest.get());
			System.out.println(passwordModel.getAddresss());
			System.out.println(passwordModel.getReissueLoginPasswordModelId());
			System.out.println(passwordModel.getBranch());
			System.out.println(passwordModel.isAtBranch());
			loginPasswordModelRepository.save(passwordModel);
			System.out.println(8);
			responsemodel.setMessage("Request Saved Successfully");
			responsemodel.setStatus(true);
			return new ResponseEntity<> (responsemodel,HttpStatus.CREATED);
			
		} catch (Exception e) {
			responsemodel.setMessage("Something Went Wrong with the DB Connection");
			responsemodel.setStatus(true);
			return new ResponseEntity<> (responsemodel,HttpStatus.SERVICE_UNAVAILABLE);
		}
	}*/
	
	public ResponseEntity<?> reissuePasswordService(ReissueLoginPasswordDTO loginPasswordModel,int customerServiceRequestId){
		ResponseModel responsemodel = new ResponseModel();
		ReissueLoginPasswordModel reissueLoginPasswordModel = new ReissueLoginPasswordModel();
		Optional<CustomerServiceRequest> customerServiceRequest = customerServiceRequestRepository.findById(customerServiceRequestId);
		if(!customerServiceRequest.isPresent()) {
			responsemodel.setMessage("There is No such service Available");
			responsemodel.setStatus(false);
			return new ResponseEntity<>(responsemodel, HttpStatus.NO_CONTENT);
		}
		int serviceRequestId = customerServiceRequest.get().getServiceRequest().getDigiFormId();
		if(serviceRequestId != ServiceRequestIdConfig.REISSUE_LOGIN_PASSWORD)
		{
			responsemodel.setMessage("Invalied Request");
			responsemodel.setStatus(false);
			return new ResponseEntity<>(responsemodel, HttpStatus.BAD_REQUEST);
		}
		NDBBranch ndbBranch = new NDBBranch();
		if(loginPasswordModel.isAtBranch()){
			if(loginPasswordModel.getBranchId() == 0) {
				responsemodel.setMessage("Complete Bank Details");
				responsemodel.setStatus(false);
				return new ResponseEntity<>(responsemodel, HttpStatus.BAD_REQUEST);
			}
			Optional<NDBBranch> branchOpt = branchRepository.findById(loginPasswordModel.getBranchId());
			if(!branchOpt.isPresent()) {
				responsemodel.setMessage("Invalied Bank Details");
				responsemodel.setStatus(false);
				return new ResponseEntity<>(responsemodel, HttpStatus.BAD_REQUEST);
			}
			ndbBranch = branchOpt.get();
			reissueLoginPasswordModel.setBranch(ndbBranch);
		}
		if(loginPasswordModel.isPostToAddress()) {
			if(loginPasswordModel.getAddresss() == null) {
				responsemodel.setMessage("Complete Address");
				responsemodel.setStatus(false);
				return new ResponseEntity<>(responsemodel, HttpStatus.BAD_REQUEST);
			}
		}
		
		Optional<ReissueLoginPasswordModel> reissueLoginPasswordOpt = loginPasswordModelRepository.getFormFromCSR(customerServiceRequestId);

		if(reissueLoginPasswordOpt.isPresent()) {
			reissueLoginPasswordModel.setReissueLoginPasswordModelId(reissueLoginPasswordOpt.get().getReissueLoginPasswordModelId());
		}
		

		reissueLoginPasswordModel.setBankingUserId(loginPasswordModel.getBankingUserId());
		reissueLoginPasswordModel.setAtBranch(loginPasswordModel.isAtBranch());
		reissueLoginPasswordModel.setPostToAddress(loginPasswordModel.isPostToAddress());
		reissueLoginPasswordModel.setAddresss(loginPasswordModel.getAddresss());
		reissueLoginPasswordModel.setPostToCorrespondenceAddress(loginPasswordModel.isPostToCorrespondenceAddress());
		reissueLoginPasswordModel.setCustomerServiceRequest(customerServiceRequest.get());
		
		try {
		loginPasswordModelRepository.save(reissueLoginPasswordModel);
		responsemodel.setMessage("Request Saved Successfully");
		responsemodel.setStatus(true);
		return new ResponseEntity<> (responsemodel,HttpStatus.CREATED);
		} catch (Exception e) {
			throw new CustomException("Something went wrong......");
		}
	}
	
	
	
	
	public ResponseEntity<?> linkJointAccounts(LinkAccountDTO accountDTO,int customerServiceRequestId){
		ResponseModel responsemodel = new ResponseModel();
		Optional<CustomerServiceRequest> customerServiceRequest = customerServiceRequestRepository.findById(customerServiceRequestId);
		if(!customerServiceRequest.isPresent()) {
			responsemodel.setMessage("There is No such service Available");
			responsemodel.setStatus(false);
			return new ResponseEntity<>(responsemodel, HttpStatus.NO_CONTENT);
		}
		
		int serviceRequestId = customerServiceRequest.get().getServiceRequest().getDigiFormId();
		if(serviceRequestId != ServiceRequestIdConfig.LINK_FOLLOWING_JOINT_ACCOUNTS)
		{
			responsemodel.setMessage("Invalied Request");
			responsemodel.setStatus(false);
			return new ResponseEntity<>(responsemodel, HttpStatus.BAD_REQUEST);
		}
		
		LinkAccountModel linkAccountModel = new LinkAccountModel();
		List<InternetBankingLinkAccountNumbers> accountNumbers = new ArrayList<>();
		
		Optional<LinkAccountModel> linkAccountOPT = linkAccountModelRepository.getFormFromCSR(customerServiceRequestId);
		if(linkAccountOPT.isPresent()) {
			linkAccountModel.setLinkAccountModelId(linkAccountOPT.get().getLinkAccountModelId());
			accountNumbers = linkAccountOPT.get().getInternetBankingLinkAccountNumbers();
			for(InternetBankingLinkAccountNumbers numbers : accountNumbers) {
				linkAccountNumbersRepository.delete(numbers);
			}

		}
		for(String s : accountDTO.getAccountNumbers()) 
		{
			InternetBankingLinkAccountNumbers accountNumber = new InternetBankingLinkAccountNumbers();
			accountNumber.setAccountNumber(s);
			accountNumber = linkAccountNumbersRepository.save(accountNumber);
			accountNumbers.add(accountNumber);
		}
		
		linkAccountModel.setExistingBankingUserId(accountDTO.getExistingBankingUserId());
		linkAccountModel.setInternetBankingLinkAccountNumbers(accountNumbers);
		linkAccountModel.setCustomerServiceRequest(customerServiceRequest.get());
		
		try {
			linkAccountModelRepository.save(linkAccountModel);
			responsemodel.setMessage("Request Created Successfully");
			responsemodel.setStatus(true);
			return new ResponseEntity<> (responsemodel,HttpStatus.CREATED);
		} catch (Exception e) {
			responsemodel.setMessage("Something Went Wrong with the DB Connection");
			responsemodel.setStatus(true);
			return new ResponseEntity<> (responsemodel,HttpStatus.SERVICE_UNAVAILABLE);
		}
	}

	public ResponseEntity<?> excludeAccountNo(LinkAccountDTO accountDTO,int customerServiceRequestId){
		ResponseModel responsemodel = new ResponseModel();
		Optional<CustomerServiceRequest> customerServiceRequest = customerServiceRequestRepository.findById(customerServiceRequestId);
		if(!customerServiceRequest.isPresent()) {
			responsemodel.setMessage("There is No such service Available");
			responsemodel.setStatus(false);
			return new ResponseEntity<>(responsemodel, HttpStatus.NO_CONTENT);
		}
		int serviceRequestId = customerServiceRequest.get().getServiceRequest().getDigiFormId();
		if(serviceRequestId != ServiceRequestIdConfig.EXCLUDE_ACCOUNTS_FROM_INTERNET_BANKING_FACILITY)
		{
			responsemodel.setMessage("Invalied Request");
			responsemodel.setStatus(false);
			return new ResponseEntity<>(responsemodel, HttpStatus.BAD_REQUEST);
		}
		ExcludeInternetAccount excludeAccountModel = new ExcludeInternetAccount();
		List<InternetBankingExcludeAccountNumbers> accountNumbers = new ArrayList<>();
		Optional<ExcludeInternetAccount> excludeAccountOPT = excludeInternetAccountRepository.getFormFromCSR(customerServiceRequestId);
		if(excludeAccountOPT.isPresent()) {
			excludeAccountModel.setExcludeInternetAccountId(excludeAccountOPT.get().getExcludeInternetAccountId());
			accountNumbers = excludeAccountOPT.get().getBankingExcludeAccountNumbers();
			for(InternetBankingExcludeAccountNumbers accountNumbers2 : accountNumbers) {
				excludeAccountNumbersRepository.delete(accountNumbers2);
			}
		}
		
		for(String s : accountDTO.getAccountNumbers()) 
		{
			InternetBankingExcludeAccountNumbers accountNumber = new InternetBankingExcludeAccountNumbers();
			accountNumber.setAccountNumber(s);
			accountNumber = excludeAccountNumbersRepository.save(accountNumber);
			accountNumbers.add(accountNumber);
		}
		excludeAccountModel.setExistingBankingUserId(accountDTO.getExistingBankingUserId());
		excludeAccountModel.setBankingExcludeAccountNumbers(accountNumbers);
		excludeAccountModel.setCustomerServiceRequest(customerServiceRequest.get());
		
		try {
			excludeInternetAccountRepository.save(excludeAccountModel);
			responsemodel.setMessage("Request Saved Successfully");
			responsemodel.setStatus(true);
			return new ResponseEntity<> (responsemodel,HttpStatus.CREATED);
		} catch (Exception e) {
			responsemodel.setMessage("Something Went Wrong with the DB Connection");
			responsemodel.setStatus(true);
			return new ResponseEntity<> (responsemodel,HttpStatus.SERVICE_UNAVAILABLE);
		}
	}

	@Override
	public ResponseEntity<?> internetOtherService(InternetBanking banking, int customerServiceRequestId) {
		ResponseModel responsemodel = new ResponseModel();
		Optional<CustomerServiceRequest> customerServiceRequest = customerServiceRequestRepository.findById(customerServiceRequestId);
		if(!customerServiceRequest.isPresent()) {
			responsemodel.setMessage("There is No such service Available");
			responsemodel.setStatus(false);
			return new ResponseEntity<>(responsemodel, HttpStatus.NO_CONTENT);
		}
		int serviceRequestId = customerServiceRequest.get().getServiceRequest().getDigiFormId();
		if(serviceRequestId != ServiceRequestIdConfig.OTHER_INTERNET_BANKING_SERVICES)
		{
			responsemodel.setMessage("Invalied Request");
			responsemodel.setStatus(false);
			return new ResponseEntity<>(responsemodel, HttpStatus.BAD_REQUEST);
		}
		
		Optional<InternetBanking> internetBankingOpt = internetBankingRepository.getFormFromCSR(customerServiceRequestId);

		banking.setCustomerServiceRequest(customerServiceRequest.get());
		if(internetBankingOpt.isPresent()) {
			banking.setInternetBankingId(internetBankingOpt.get().getInternetBankingId());
		}
		try {
			internetBankingRepository.save(banking);
			responsemodel.setMessage("service Saved Successfully");
			responsemodel.setStatus(true);
			return new ResponseEntity<>(responsemodel, HttpStatus.CREATED);
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}
}
