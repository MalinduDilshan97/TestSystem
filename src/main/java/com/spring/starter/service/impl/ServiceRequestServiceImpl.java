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

import com.spring.starter.DTO.SignatureDTO;
import com.spring.starter.Repository.*;
import com.spring.starter.model.*;
import com.spring.starter.util.FileStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.spring.starter.DTO.CustomerDTO;
import com.spring.starter.Exception.CustomException;
import com.spring.starter.Repository.AtmOrDebitCardRequestRepository;
import com.spring.starter.model.AtmOrDebitCardRequest;
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
    private StaffUserRepository staffUserRepository;

    @Autowired
    private AtmOrDebitCardRequestRepository atmOrDebitCardRequestRepository;
    @Autowired
    private FileStorage fileStorage;
    private ResponseModel res = new ResponseModel();

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
        Customer customer = new Customer();
/*        Optional<Customer> customerOpt = customerRepository.getCustomerFromIdentity(customerDTO.getIdentification());
        if (customerOpt.isPresent()) {
            customer = customerOpt.get();
        } else {
            customer = new Customer();
        }*/
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

    public ResponseEntity<?> addAServiceToACustomer(int customerId, int serviceRequestId) {
        ResponseModel responsemodel = new ResponseModel();
        Optional<Customer> customerOpt = customerRepository.findById(customerId);
        if (!customerOpt.isPresent()) {
            responsemodel.setMessage("Customer is not avalilable");
            responsemodel.setStatus(false);
            return new ResponseEntity<>(responsemodel, HttpStatus.BAD_REQUEST);
        }
        Optional<ServiceRequest> serviceRequestOpt = serviceRequestRepository.getFromDigiId(serviceRequestId);
        if (!serviceRequestOpt.isPresent()) {
            responsemodel.setMessage("Invalied bank service");
            responsemodel.setStatus(false);
            return new ResponseEntity<>(responsemodel, HttpStatus.BAD_REQUEST);
        }
        CustomerServiceRequest customerServiceRequest = new CustomerServiceRequest();
        customerServiceRequest.setCustomer(customerOpt.get());
        customerServiceRequest.setServiceRequest(serviceRequestOpt.get());
        customerServiceRequest.setRequestDate(java.util.Calendar.getInstance().getTime());
        customerServiceRequest = customerServiceRequestRepository.save(customerServiceRequest);

        responsemodel.setMessage("Service Created Successfully");
        responsemodel.setStatus(true);
        return new ResponseEntity<>(responsemodel, HttpStatus.OK);
    }

    public ResponseEntity<?> getAllServiceRequests(int customerId, String date) {
        ResponseModel responsemodel = new ResponseModel();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date requestDate;
        List<CustomerServiceRequest> customerServiceRequests = new ArrayList<>();
        try {
            requestDate = df.parse(date);
            System.out.println(date);
            customerServiceRequests = customerServiceRequestRepository.getrequestsByDateAndCustomer(customerId, requestDate);
            System.out.println(customerServiceRequests.size());
            return new ResponseEntity<>(customerServiceRequests, HttpStatus.OK);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        responsemodel.setMessage("blablabla");
        responsemodel.setStatus(true);
        return new ResponseEntity<>(responsemodel, HttpStatus.OK);
    }

    public ResponseEntity<?> getAllCustomerRequests(int customerId) {
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

    public ResponseEntity<?> getCustomerDetailsWithServiceRequests(int customerId) {
        ResponseModel responsemodel = new ResponseModel();
        try {
            Optional<Customer> customerOpt = customerRepository.findById(customerId);
            if (!customerOpt.isPresent()) {
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

    public ResponseEntity<?> completeARequest(Principal principal, int requestId) {
        ResponseModel responsemodel = new ResponseModel();
        Optional<CustomerServiceRequest> customerServiceRequestOPT = customerServiceRequestRepository.findById(requestId);
        CustomerServiceRequest customerServiceRequest = customerServiceRequestOPT.get();
        List<StaffUser> staffHandled;
        if (customerServiceRequest.getStaffUser().isEmpty()) {
            staffHandled = new ArrayList<>();
        } else {
            staffHandled = customerServiceRequest.getStaffUser();
        }

        Optional<StaffUser> staffUser = staffUserRepository.findById(Integer.parseInt(principal.getName()));
        staffHandled.add(staffUser.get());
        customerServiceRequest.setStatus(true);
        customerServiceRequest.setStaffUser(staffHandled);
        CustomerServiceRequest save = customerServiceRequestRepository.save(customerServiceRequest);
        if (save!=null){
            responsemodel.setMessage("User Request finished successfully");
            responsemodel.setStatus(true);
            return new ResponseEntity<>(responsemodel, HttpStatus.CREATED);
        }else{
            responsemodel.setMessage("User Request Unsuccessful");
            responsemodel.setStatus(true);
            return new ResponseEntity<>(responsemodel, HttpStatus.BAD_REQUEST);
        }

    }

    public ResponseEntity<?> addAStaffHandled(Principal principal, int requestId) {
        ResponseModel responsemodel = new ResponseModel();
        Optional<CustomerServiceRequest> customerServiceRequestOPT = customerServiceRequestRepository.findById(requestId);
        CustomerServiceRequest customerServiceRequest = customerServiceRequestOPT.get();
        List<StaffUser> staffHandled;
        if (customerServiceRequest.getStaffUser().isEmpty()) {
            staffHandled = new ArrayList<>();
        } else {
            staffHandled = customerServiceRequest.getStaffUser();
        }

        Optional<StaffUser> staffUser = staffUserRepository.findById(Integer.parseInt(principal.getName()));
        staffHandled.add(staffUser.get());
        customerServiceRequest.setStaffUser(staffHandled);
        CustomerServiceRequest save = customerServiceRequestRepository.save(customerServiceRequest);
        if (save!=null){
            responsemodel.setMessage("Part OF User Request finished successfully");
            responsemodel.setStatus(true);
            return new ResponseEntity<>(responsemodel, HttpStatus.CREATED);
        }else{
            responsemodel.setMessage("User Request Unsuccessful");
            responsemodel.setStatus(true);
            return new ResponseEntity<>(responsemodel, HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<?> completeAllCustomerRequests(Principal principal, int customerId) {
        ResponseModel responsemodel = new ResponseModel();
        List<CustomerServiceRequest> allCustomers = customerServiceRequestRepository.getAllCustomerRequest(customerId);


        for (CustomerServiceRequest customerServiceRequest : allCustomers) {
            List<StaffUser> staffHandled;
            CustomerServiceRequest request = customerServiceRequest;
            request.setStatus(true);
            if (customerServiceRequest.getStaffUser().isEmpty()) {
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
        responsemodel.setMessage("All the request of " + customerId + " get updated successfully");
        responsemodel.setStatus(true);
        return new ResponseEntity<>(responsemodel, HttpStatus.CREATED);
    }

    public Optional<Customer> getCustomer() {
        return customerRepository.findById(1);
    }

    @Override
    public ResponseEntity<?> getBankServices() {
        List<ServiceRequest> bankServises = serviceRequestRepository.findAll();
        if (bankServises.isEmpty()) {
            ResponseModel responsemodel = new ResponseModel();
            responsemodel.setMessage("There is no bank servieces available yet");
            responsemodel.setStatus(true);
            return new ResponseEntity<>(responsemodel, HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(bankServises, HttpStatus.OK);
        }
    }
/*	@Override
	public ResponseEntity<?> atmOrDebitCardRequest(AtmOrDebitCardRequest atmOrDebit, int customerServiceRequestId)
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
		List<AtmOrDebitCardRequest> atmOrDebits = new ArrayList<>();
		CustomerServiceRequestForm customerServiceRequestForm;
		Optional<CustomerServiceRequestForm> csrfOpt = customerServiceRequestFormRepository.getCustomerServiceRequestFormForCustomerServiceRequest(customerServiceRequestId); 
		if(csrfOpt.isPresent()) {
			customerServiceRequestForm = csrfOpt.get();
			if(!customerServiceRequestForm.getAtmOrDebitCardRequest().isEmpty())
			{
				atmOrDebits = customerServiceRequestForm.getAtmOrDebitCardRequest();
			}
		} else {
			customerServiceRequestForm = new CustomerServiceRequestForm();
			customerServiceRequestForm.setCustomerServiceRequest(customerServiceRequest.get());
			customerServiceRequestForm.setCustomer(customerServiceRequest.get().getCustomer());
		}
		atmOrDebit = atmOrDebitCardRequestRepository.save(atmOrDebit);
		atmOrDebits.add(atmOrDebit);
		
		customerServiceRequestForm.setAtmOrDebitCardRequest(atmOrDebits);
		customerServiceRequestForm = customerServiceRequestFormRepository.save(customerServiceRequestForm);
		return new ResponseEntity<>(customerServiceRequestForm,HttpStatus.CREATED);
	}*/

    @Override
    public ResponseEntity<?> getServiceRequestForm(int customerServiceRequestId) {
        ResponseModel responsemodel = new ResponseModel();
        Optional<CustomerServiceRequest> customerServiceRequest = customerServiceRequestRepository.findById(customerServiceRequestId);
        if (!customerServiceRequest.isPresent()) {
            responsemodel.setMessage("There is No such service Available");
            responsemodel.setStatus(false);
            return new ResponseEntity<>(responsemodel, HttpStatus.NO_CONTENT);
        }
        int serviceRequestId = customerServiceRequest.get().getServiceRequest().getDigiFormId();
        if (serviceRequestId == 2) {
            System.out.println("entred1");
            Optional<AtmOrDebitCardRequest> aodOptional = atmOrDebitCardRequestRepository.getFormFromCSR(customerServiceRequestId);
            if (!aodOptional.isPresent()) {
                responsemodel.setMessage("Customer Havent fill the form yet");
                responsemodel.setStatus(false);
                return new ResponseEntity<>(responsemodel, HttpStatus.NO_CONTENT);
            } else {
                return new ResponseEntity<>(aodOptional, HttpStatus.OK);
            }
        }
        responsemodel.setMessage("There is no form");
        responsemodel.setStatus(false);
        return new ResponseEntity<>(responsemodel, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> saveSignature(SignatureDTO signatureDTO) {

        Optional<CustomerServiceRequest> optional=customerServiceRequestRepository.findById(signatureDTO.getService_request_id());
        if (!optional.isPresent()){
            res.setMessage(" No Data Found To Complete The Request");
            res.setStatus(false);
            return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
        }

//        creating a new Path
        String location = ("/" + signatureDTO.getService_request_id()+"/ Customer Signature");
//        Saving and getting storage url
        String url = fileStorage.fileSave(signatureDTO.getFile(), location);
//        Checking Is File Saved ?
		if(url.equals("Failed")) {
            res.setMessage(" Failed To Upload Signature");
            res.setStatus(false);
            return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
        }else{

		    CustomerServiceRequest customerServiceRequest = optional.get();
		    customerServiceRequest.setUrl(url);

		    if (customerServiceRequestRepository.save(customerServiceRequest)!=null){
                res.setMessage(" Request Form Successfully Saved To The System");
                res.setStatus(true);
                return new ResponseEntity<>(res, HttpStatus.OK);
            }else{
                res.setMessage(" Failed TO Save The Request... Operation Unsuccessful");
                res.setStatus(false);
                return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
            }

        }
    }
}
