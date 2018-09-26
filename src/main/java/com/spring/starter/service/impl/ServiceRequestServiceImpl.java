package com.spring.starter.service.impl;

import java.security.Principal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.spring.starter.DTO.CustomerDTO;
import com.spring.starter.Exception.CustomException;
import com.spring.starter.Repository.AtmOrDebitRepository;
import com.spring.starter.Repository.CustomerAccountNoRepository;
import com.spring.starter.Repository.CustomerRepository;
import com.spring.starter.Repository.CustomerServiceRequestRepository;
import com.spring.starter.Repository.ServiceRequestRepository;
import com.spring.starter.Repository.StaffUserRepository;
import com.spring.starter.model.AtmOrDebit;
import com.spring.starter.model.Customer;
import com.spring.starter.model.CustomerAccountNo;
import com.spring.starter.model.CustomerServiceRequest;
import com.spring.starter.model.ResponseModel;
import com.spring.starter.model.ServiceRequest;
import com.spring.starter.model.StaffUser;
import com.spring.starter.service.ServiceRequestService;

@Service
@Transactional
public class ServiceRequestServiceImpl implements ServiceRequestService {

	@Autowired
	private ServiceRequestRepository serviceRequestRepository;

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private CustomerAccountNoRepository customerAccountNoRepository;
	
	@Autowired
	private CustomerServiceRequestRepository customerServiceRequestRepository; 

	@Autowired
	private StaffUserRepository  staffUserRepository;
	
	@Autowired
	private AtmOrDebitRepository atmOrDebitRepository;
	
	@Override
	public ResponseEntity<?> addNewServiceRequest(ServiceRequest serviceRequest) {

		ResponseModel responsemodel = new ResponseModel();
		try {
			serviceRequestRepository.save(serviceRequest);
			responsemodel.setMessage("Service Saved Successfully");
			responsemodel.setStatus(true);
			return new ResponseEntity<>(responsemodel, HttpStatus.CREATED);
		} catch (Exception e) {
			throw new CustomException("Something went wrong with the DB Connection");
		}
	}

	@Override
	public ResponseEntity<?> addNewCustomer(CustomerDTO customerDTO) {
		ResponseModel responsemodel = new ResponseModel();

		List<String> accNo = customerDTO.getAccountNos();
		Customer customer;
		Optional<Customer> customerOpt = customerRepository.getCustomerFromIdentity(customerDTO.getIdentification());
		if(customerOpt.isPresent()) {
			customer = customerOpt.get();
		} else {
			customer = new Customer();
		}
		customer.setIdentification(customerDTO.getIdentification());
		customer.setName(customerDTO.getName());
		customer.setMobileNo(customerDTO.getMobileNo());
		Customer customer_new;
		try {
			customer_new = customerRepository.save(customer);
		} catch (Exception e) {
			responsemodel.setMessage("Something wrong with the database connection");
			responsemodel.setStatus(false);
			return new ResponseEntity<>(responsemodel, HttpStatus.SERVICE_UNAVAILABLE);
		}
		List<CustomerAccountNo> customerAccountNumbers = new ArrayList<>();

		for (String s : accNo) {
			CustomerAccountNo customerAccountNo = new CustomerAccountNo();
			customerAccountNo.setCustomer(customer);
			customerAccountNo.setAccountNumber(s);
			customerAccountNo = customerAccountNoRepository.save(customerAccountNo);
			customerAccountNumbers.add(customerAccountNo);
		}
		responsemodel.setMessage("Customer Saved Successfully");
		responsemodel.setStatus(true);
		return new ResponseEntity<>(responsemodel, HttpStatus.CREATED);
	}
	
	public ResponseEntity<?> addAServiceToACustomer(int customerId, int serviceRequestId)
	{
		ResponseModel responsemodel = new ResponseModel();
		Optional<Customer> customerOpt = customerRepository.findById(customerId);
		if(!customerOpt.isPresent()) {
			responsemodel.setMessage("Customer is not avalilabe");
			responsemodel.setStatus(false);
			return new ResponseEntity<>(responsemodel, HttpStatus.BAD_REQUEST);
		}
		Optional<ServiceRequest> serviceRequestOpt = serviceRequestRepository.findById(serviceRequestId);
		if(!serviceRequestOpt.isPresent()) {
			responsemodel.setMessage("Invalied bank service");
			responsemodel.setStatus(false);
			return new ResponseEntity<>(responsemodel, HttpStatus.BAD_REQUEST);
		}
		CustomerServiceRequest customerServiceRequest = new CustomerServiceRequest();
		customerServiceRequest.setCustomer(customerOpt.get());
		customerServiceRequest.setServiceRequest(serviceRequestOpt.get());
		customerServiceRequest.setRequestDate(java.util.Calendar.getInstance().getTime());
		customerServiceRequest= customerServiceRequestRepository.save(customerServiceRequest);
		
		responsemodel.setMessage("Service Created Successfully");
		responsemodel.setStatus(true);
		return new ResponseEntity<>(responsemodel, HttpStatus.OK);
	}
	
	public ResponseEntity<?> getAllServiceRequests(int customerId,String date)
	{
		ResponseModel responsemodel = new ResponseModel();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date requestDate;
		List<CustomerServiceRequest> customerServiceRequests= new ArrayList<>();
		try {
			requestDate = df.parse(date);
			System.out.println(date);
			customerServiceRequests = customerServiceRequestRepository.getrequestsByDateAndCustomer(customerId, requestDate);
			System.out.println(customerServiceRequests.size());
			return new ResponseEntity<>(customerServiceRequests,HttpStatus.OK);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		responsemodel.setMessage("blablabla");
		responsemodel.setStatus(true);
		return new ResponseEntity<>(responsemodel, HttpStatus.OK);
	}
	
	public ResponseEntity<?> getAllCustomerRequests(int customerId)
	{
		try {
			List<CustomerServiceRequest> customerServiceRequests = customerServiceRequestRepository.getAllCustomerRequest(customerId);
			return new ResponseEntity<>(customerServiceRequests, HttpStatus.OK);
		} catch (Exception e) {
			ResponseModel responsemodel = new ResponseModel();
			responsemodel.setMessage("There is a problem with the database connection");
			responsemodel.setStatus(true);
			return new ResponseEntity<>(responsemodel, HttpStatus.SERVICE_UNAVAILABLE);
		}
	}
	
	public ResponseEntity<?> getCustomerDetailsWithServiceRequests(int customerId)
	{
		ResponseModel responsemodel = new ResponseModel();
		try {
			Optional<Customer> customerOpt = customerRepository.findById(customerId);
			if(!customerOpt.isPresent()) 
			{
				responsemodel.setMessage("There is no customer for that ID");
				responsemodel.setStatus(true);
				return new ResponseEntity<>(responsemodel, HttpStatus.BAD_REQUEST);
			}
			Customer customer = customerOpt.get();
			List<CustomerServiceRequest> customerServiceRequests = customerServiceRequestRepository.getAllCustomerRequest(customerId);
			customer.setCustomerServiceRequests(customerServiceRequests);
			return new ResponseEntity<>(customer, HttpStatus.OK);
		} catch (Exception e) {
			responsemodel.setMessage("There is a problem with the database connection");
			responsemodel.setStatus(true);
			return new ResponseEntity<>(responsemodel, HttpStatus.SERVICE_UNAVAILABLE);
		}
	}
	
	public ResponseEntity<?> completeARequest(Principal principal,int requestId)
	{
		ResponseModel responsemodel = new ResponseModel();
		Optional<CustomerServiceRequest> customerServiceRequestOPT = customerServiceRequestRepository.findById(requestId);
		CustomerServiceRequest customerServiceRequest =customerServiceRequestOPT.get();
		List<StaffUser> staffHandled;
		if(customerServiceRequest.getStaffUser().isEmpty()) 
		{
			staffHandled = new ArrayList<>();
		} else {
			staffHandled = customerServiceRequest.getStaffUser();
		}
		
		Optional<StaffUser> staffUser = staffUserRepository.findById(Integer.parseInt(principal.getName()));
		staffHandled.add(staffUser.get());
		customerServiceRequest.setStatus(true);
		customerServiceRequest.setStaffUser(staffHandled);
		responsemodel.setMessage("User Request finished successfully");
		responsemodel.setStatus(true);
		return new ResponseEntity<>(responsemodel, HttpStatus.CREATED);
	}
	
	public ResponseEntity<?> addAStaffHandled(Principal principal, int requestId)
	{
		ResponseModel responsemodel = new ResponseModel();
		Optional<CustomerServiceRequest> customerServiceRequestOPT = customerServiceRequestRepository.findById(requestId);
		CustomerServiceRequest customerServiceRequest =customerServiceRequestOPT.get();
		List<StaffUser> staffHandled;
		if(customerServiceRequest.getStaffUser().isEmpty()) 
		{
			staffHandled = new ArrayList<>();
		} else {
			staffHandled = customerServiceRequest.getStaffUser();
		}
		
		Optional<StaffUser> staffUser = staffUserRepository.findById(Integer.parseInt(principal.getName()));
		staffHandled.add(staffUser.get());
		customerServiceRequest.setStaffUser(staffHandled);
		responsemodel.setMessage("User Request finished successfully");
		responsemodel.setStatus(true);
		return new ResponseEntity<>(responsemodel, HttpStatus.CREATED);
	}
	
	public ResponseEntity<?> completeAllCustomerRequests(Principal principal, int customerId)
	{
		ResponseModel responsemodel = new ResponseModel();
		List<CustomerServiceRequest> allCustomers = customerServiceRequestRepository.getAllCustomerRequest(customerId);
		List<StaffUser> staffHandled;

		for (CustomerServiceRequest customerServiceRequest : allCustomers) {
			CustomerServiceRequest request = new CustomerServiceRequest();
			request.setStatus(true);
			if(customerServiceRequest.getStaffUser().isEmpty()) 
			{
				staffHandled = new ArrayList<>();
			} else {
				staffHandled = customerServiceRequest.getStaffUser();
			}
			request.setStaffUser(staffHandled);
			try {
				customerServiceRequestRepository.save(request);

			} catch (Exception e) {
				responsemodel.setMessage("Something Went Wrong With the Connection");
				responsemodel.setStatus(false);
				return new ResponseEntity<>(responsemodel, HttpStatus.SERVICE_UNAVAILABLE);
			}
		}
		responsemodel.setMessage("All the request of "+ customerId +" get updated successfully");
		responsemodel.setStatus(true);
		return new ResponseEntity<>(responsemodel, HttpStatus.CREATED);
	}
	
	public Optional<Customer> getCustomer() 
	{
		return customerRepository.findById(1);
	}

	@Override
	public ResponseEntity<?> getBankServices() {
		List<ServiceRequest> bankServises = serviceRequestRepository.findAll();
		if(bankServises.isEmpty()) {
			ResponseModel responsemodel = new ResponseModel();
			responsemodel.setMessage("There is no bank servieces available yet");
			responsemodel.setStatus(true);
			return new ResponseEntity<>(responsemodel, HttpStatus.NO_CONTENT);
		} else {
			return new ResponseEntity<>(bankServises, HttpStatus.OK);
		}
	}
/*	@Override
	public ResponseEntity<?> atmOrDebitCardRequest(AtmOrDebit atmOrDebit, int customerServiceRequestId)
	{
		ResponseModel responsemodel = new ResponseModel();
		Optional<CustomerServiceRequest> customerServiceRequest = customerServiceRequestRepository.findById(customerServiceRequestId);
		if(!customerServiceRequest.isPresent()) {
			responsemodel.setMessage("There is No such service Available");
			responsemodel.setStatus(false);
			return new ResponseEntity<>(responsemodel, HttpStatus.NO_CONTENT);
		}
		int serviceRequestId = customerServiceRequest.get().getServiceRequest().getServiceRequestId();
		if(serviceRequestId != 2) 
		{
			responsemodel.setMessage("Invalied Request");
			responsemodel.setStatus(false);
			return new ResponseEntity<>(responsemodel, HttpStatus.BAD_REQUEST);
		}
		List<AtmOrDebit> atmOrDebits = new ArrayList<>();
		CustomerServiceRequestForm customerServiceRequestForm;
		Optional<CustomerServiceRequestForm> csrfOpt = customerServiceRequestFormRepository.getCustomerServiceRequestFormForCustomerServiceRequest(customerServiceRequestId); 
		if(csrfOpt.isPresent()) {
			customerServiceRequestForm = csrfOpt.get();
			if(!customerServiceRequestForm.getAtmOrDebit().isEmpty()) 
			{
				atmOrDebits = customerServiceRequestForm.getAtmOrDebit();
			}
		} else {
			customerServiceRequestForm = new CustomerServiceRequestForm();
			customerServiceRequestForm.setCustomerServiceRequest(customerServiceRequest.get());
			customerServiceRequestForm.setCustomer(customerServiceRequest.get().getCustomer());
		}
		atmOrDebit = atmOrDebitRepository.save(atmOrDebit);
		atmOrDebits.add(atmOrDebit);
		
		customerServiceRequestForm.setAtmOrDebit(atmOrDebits);
		customerServiceRequestForm = customerServiceRequestFormRepository.save(customerServiceRequestForm);
		return new ResponseEntity<>(customerServiceRequestForm,HttpStatus.CREATED);
	}*/

	@Override
	public ResponseEntity<?> getServiceRequestForm(int customerServiceRequestId)
	{
		ResponseModel responsemodel = new ResponseModel();
		Optional<CustomerServiceRequest> customerServiceRequest = customerServiceRequestRepository.findById(customerServiceRequestId);
		if(!customerServiceRequest.isPresent()) {
			responsemodel.setMessage("There is No such service Available");
			responsemodel.setStatus(false);
			return new ResponseEntity<>(responsemodel, HttpStatus.NO_CONTENT);
		}
		int serviceRequestId = customerServiceRequest.get().getServiceRequest().getDigiFormId();
		if(serviceRequestId == 2) 
		{
			System.out.println("entred1");
			Optional<AtmOrDebit> aodOptional =  atmOrDebitRepository.getFormFromCSR(customerServiceRequestId);
			if(!aodOptional.isPresent()) 
			{
				responsemodel.setMessage("Customer Havent fill the form yet");
				responsemodel.setStatus(false);
				return new ResponseEntity<>(responsemodel, HttpStatus.NO_CONTENT);
			}
			else 
			{
				return new ResponseEntity<>(aodOptional,HttpStatus.OK);
			}
		}
		responsemodel.setMessage("There is no form");
		responsemodel.setStatus(false);
		return new ResponseEntity<>(responsemodel, HttpStatus.OK);
	}
}
