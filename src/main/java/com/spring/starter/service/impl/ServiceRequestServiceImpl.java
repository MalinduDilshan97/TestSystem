package com.spring.starter.service.impl;

import java.security.Principal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import com.spring.starter.DTO.DocumentDTO;
import com.spring.starter.DTO.SignatureDTO;
import com.spring.starter.Repository.*;
import com.spring.starter.configuration.ServiceRequestIdConfig;
import com.spring.starter.model.*;
import com.spring.starter.util.FileStorage;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.spring.starter.DTO.CustomerDTO;
import com.spring.starter.Exception.CustomException;
import com.spring.starter.Repository.AtmOrDebitCardRequestRepository;
import com.spring.starter.model.AtmOrDebitCardRequest;
import com.spring.starter.service.ServiceRequestService;
import org.springframework.web.multipart.MultipartFile;

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

    @Autowired
    private SmsSubscriptionRepository smsSubscriptionRepository;

    @Autowired
    private ReIssuePinRequestRepository reIssuePinRequestRepository;

    @Autowired
    private PosLimitRepository posLimitRepository;

    @Autowired
    private LinkedAccountRepository linkedAccountRepository;

    @Autowired
    private ChangePrimaryAccountRepository changePrimaryAccountRepository;

    @Autowired
    private ChangeMailingMailModelRepository changeMailingMailModelRepository;

    @Autowired
    private ChangePermanentMailRepository changePermanentMailRepository;

    @Autowired
    private ReissueLoginPasswordModelRepository loginPasswordModelRepository;

    @Autowired
    private  LinkAccountModelRepository linkAccountModelRepository;

    @Autowired
    private ExcludeInternetAccountRepository excludeInternetAccountRepository;

    @Autowired
    private InternetBankingRepository internetBankingRepository;

    @Autowired
    private SMSAlertsForCreditCardRepository AlertsForCreditCardRepository;

    @Autowired
    private ChangeIdentificationFormRepository changeIdentificationFormRepository;

    @Autowired
    private ContactDetailsRepository contactDetailsRepository;

    @Autowired
    private AccountStatementIssueRequestRepository accountStatementIssueRequestRepository;

    @Autowired
    private DuplicatePassBookRequestRepository duplicatePassBookRequestRepository;

    @Autowired
    EstatementFacilityRepository estatementFacilityRepository;

    @Autowired
    StatementFrequencyRepository statementFrequencyRepository;

    @Autowired
    WithholdingFdCdRepository withholdingFdCdRepository;

    @Autowired
    OtherFdCdRelatedRequestRepository otherFdCdRelatedRequestRepository;

    @Autowired
    DuplicateFdCdCertRepository duplicateFdCdCertRepository;

    @Autowired
    OtherServiceRequestRepository otherServiceRequestRepository;

    @Autowired
    ServiceRequestCustomerLogRepository serviceRequestCustomerLogRepository;

    @Autowired
    ServiceRequestTifRepository serviceRequestTifRepository;

    @Autowired
    private EffectOrRevokePaymentRepository effectOrRevokePaymentRepository;



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
    public ResponseEntity<?> addNewCustomer(CustomerDTO customerDTO,HttpServletRequest request) {
        ResponseModel responsemodel = new ResponseModel();
                List<String> accNo = customerDTO.getAccountNos();
        Customer customer = new Customer();
/*        Optional<Customer> customerOpt = customerRepository.getCustomerFromIdentity(customerDTO.getIdentification());
        if (customerOpt.isPresent()) {
            customer = customerOpt.get();
        } else {
            customer = new Customer();
        }*/

        if(customerDTO.getName() == null || customerDTO.getIdentification() == null){
            responsemodel.setMessage("please fill the data");
            responsemodel.setStatus(false);
            return new ResponseEntity<>(responsemodel,HttpStatus.BAD_REQUEST);
        }


        ServiceRequestCustomerLog serviceRequestCustomerLog = new ServiceRequestCustomerLog();
        serviceRequestCustomerLog.setDate(java.util.Calendar.getInstance().getTime());
        serviceRequestCustomerLog.setIdentification(customerDTO.getIdentification());
        serviceRequestCustomerLog.setIp(request.getRemoteAddr());

        customer.setIdentification(customerDTO.getIdentification());
        customer.setName(customerDTO.getName());
        customer.setMobileNo(customerDTO.getMobileNo());
        customer.setDate(java.util.Calendar.getInstance().getTime());
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

        serviceRequestCustomerLog.setMessage("Customer Saved Succcessfully");
        try {
            serviceRequestCustomerLog = serviceRequestCustomerLogRepository.save(serviceRequestCustomerLog);
            System.out.println(serviceRequestCustomerLog);
             }
        catch (Exception e){
              responsemodel.setMessage(e.getMessage());
              responsemodel.setStatus(false);
              return new ResponseEntity<>(responsemodel, HttpStatus.SERVICE_UNAVAILABLE);
        }
        return new ResponseEntity<>(customer_new, HttpStatus.CREATED);
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
        return new ResponseEntity<>(customerServiceRequest, HttpStatus.OK);
    }

    public ResponseEntity<?> getAllServiceRequests(int customerId, String date) {
        ResponseModel responsemodel = new ResponseModel();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date requestDate;
        List<CustomerServiceRequest> customerServiceRequests = new ArrayList<>();
        try {
            requestDate = df.parse(date);
            customerServiceRequests = customerServiceRequestRepository.getrequestsByDateAndCustomer(customerId, requestDate);
            return new ResponseEntity<>(customerServiceRequests, HttpStatus.OK);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        responsemodel.setMessage("Success");
        responsemodel.setStatus(true);
        return new ResponseEntity<>(responsemodel, HttpStatus.OK);
    }

    public ResponseEntity<?> getAllCustomerRequests(int customerId) {
        ResponseModel responsemodel = new ResponseModel();
        Optional<Customer> customerOptional = customerRepository.findById(customerId);
        if(!customerOptional.isPresent()){
            responsemodel.setMessage("There is a no such customer available");
            responsemodel.setStatus(true);
            return new ResponseEntity<>(responsemodel, HttpStatus.NO_CONTENT);
        }
        try {
            List<CustomerServiceRequest> customerServiceRequests = customerServiceRequestRepository.getAllCustomerRequest(customerId);
            return new ResponseEntity<>(customerServiceRequests, HttpStatus.OK);
        } catch (Exception e) {
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
        if(!staffUser.isPresent()){
            responsemodel.setMessage("Identified as a Malicious login");
            responsemodel.setStatus(true);
            return new ResponseEntity<>(responsemodel, HttpStatus.CREATED);
        }

        if(customerServiceRequest.getUrl() == null || customerServiceRequest.getUrl().isEmpty()){
            responsemodel.setMessage("Plese complete the signature before completing the request");
            responsemodel.setStatus(true);
            return new ResponseEntity<>(responsemodel, HttpStatus.UNAVAILABLE_FOR_LEGAL_REASONS);
        }

        if(checkFormexists(customerServiceRequest.getServiceRequest().getDigiFormId(),requestId)){
            responsemodel.setMessage("Please complete the form before verifying");
            responsemodel.setStatus(true);
            return new ResponseEntity<>(responsemodel, HttpStatus.CREATED);
        }

        if(customerServiceRequest.isStatus()){
            responsemodel.setMessage("Request is already varified");
            responsemodel.setStatus(true);
            return new ResponseEntity<>(responsemodel, HttpStatus.I_AM_A_TEAPOT);
        }

        staffHandled.add(staffUser.get());
        customerServiceRequest.setStatus(true);
        customerServiceRequest.setStaffUser(staffHandled);
        customerServiceRequest.setRequestCompleteDate(java.util.Calendar.getInstance().getTime());

        customerServiceRequest = customerServiceRequestRepository.save(customerServiceRequest);

        responsemodel.setMessage("User Request updated successfully");
        responsemodel.setStatus(true);
        return new ResponseEntity<>(customerServiceRequest, HttpStatus.CREATED);
    }

    public ResponseEntity<?> forceCompleteARequest(Principal principal, int requestId) {
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
        if(!staffUser.isPresent()){
            responsemodel.setMessage("Unauthorized user");
            responsemodel.setStatus(false);
            return new ResponseEntity<>(responsemodel, HttpStatus.UNAUTHORIZED);
        }
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
        Optional<Customer> customerOpt = customerRepository.findById(customerId);
        if(customerOpt.isPresent()) {
            List<CustomerServiceRequest> allCustomers = customerServiceRequestRepository.getAllCustomerRequest(customerId);
            Optional<StaffUser> staffUser = staffUserRepository.findById(Integer.parseInt(principal.getName()));
            if (!staffUser.isPresent()) {
                responsemodel.setMessage("Unauthorized user");
                responsemodel.setStatus(false);
                return new ResponseEntity<>(responsemodel, HttpStatus.UNAUTHORIZED);
            }

            for (CustomerServiceRequest customerServiceRequest : allCustomers) {
                List<StaffUser> staffHandled;
                CustomerServiceRequest request = customerServiceRequest;
                request.setStatus(true);
                if (customerServiceRequest.getStaffUser().isEmpty()) {
                    staffHandled = new ArrayList<>();
                } else {
                    staffHandled = customerServiceRequest.getStaffUser();
                }
                staffHandled.add(staffUser.get());
                request.setStaffUser(staffHandled);

                try {
                    customerServiceRequestRepository.save(request);

                } catch (Exception e) {
                    responsemodel.setMessage("Something Went Wrong With the Connection");
                    responsemodel.setStatus(false);
                    return new ResponseEntity<>(responsemodel, HttpStatus.SERVICE_UNAVAILABLE);
                }

            }
            Customer customer = customerOpt.get();
            customer.setStatus(1);
            customer = customerRepository.save(customer);
            return new ResponseEntity<>(customer, HttpStatus.OK);
        }
         else  {
            ResponseModel responseModel = new ResponseModel();
            return new ResponseEntity<>(responseModel,HttpStatus.BAD_REQUEST);
        }
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
    public ResponseEntity<?> getTifs(String date) throws CustomException {
        ResponseModel responseModel = new ResponseModel();

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date requestDate;
        try {
            requestDate = df.parse(date);

            List<ServiceRequestTif> serviceRequestTifs = serviceRequestTifRepository.getTifsOfADate(requestDate);
            if (serviceRequestTifs.isEmpty()) {
                responseModel.setMessage("There are no tifs for that day");
                responseModel.setStatus(false);
                return new ResponseEntity<>(responseModel, HttpStatus.NO_CONTENT);
            } else {
                return new ResponseEntity<>(serviceRequestTifs, HttpStatus.OK);
            }
        } catch (Exception e) {
            throw new CustomException(e.getMessage());
        }
    }

    @Override
    public ResponseEntity<?> saveTif(MultipartFile file,
                                     int serviceRequestCustomerId,
                                     int queueId) throws Exception {
        ResponseModel responseModel = new ResponseModel();
        Optional<Customer> customerOptional = customerRepository.findById(serviceRequestCustomerId);
        if(!customerOptional.isPresent()){
            responseModel.setMessage("Invalied Customer record");
            responseModel.setStatus(false);
            return new ResponseEntity<>(responseModel,HttpStatus.BAD_REQUEST);
        } else {

            UUID uuid = UUID.randomUUID();
            String randomUUIDString = uuid.toString();

            String extention = file.getOriginalFilename();
            extention = FilenameUtils.getExtension(extention);

            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
            Date date = new Date();
            String location = ("/serviceRequest/tif/" + formatter.format(date));
            String filename = serviceRequestCustomerId + "." + extention;
            String url = fileStorage.fileSaveWithRenaming(file, location, filename);
            location = location + "/" + filename;
            if (url.equals("Failed")) {
                responseModel.setMessage(" Failed To Upload File");
                responseModel.setStatus(false);
                return new ResponseEntity<>(responseModel, HttpStatus.BAD_REQUEST);
            } else {
                ServiceRequestTif serviceRequestTif = new ServiceRequestTif();
                serviceRequestTif.setCustomer(customerOptional.get());
                serviceRequestTif.setDate(java.util.Calendar.getInstance().getTime());
                serviceRequestTif.setQueueId(queueId);
                serviceRequestTif.setUrl(location);

                try {
                    serviceRequestTifRepository.save(serviceRequestTif);
                    responseModel.setMessage("tif saved successfully");
                    responseModel.setStatus(true);
                    return new ResponseEntity<>(responseModel,HttpStatus.CREATED);
                } catch (Exception e) {
                    throw new Exception(e.getMessage());
                }
            }
        }
    }


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
        if (serviceRequestId == ServiceRequestIdConfig.CARD_REQUEST) {
            Optional<AtmOrDebitCardRequest> aodOptional = atmOrDebitCardRequestRepository.getFormFromCSR(customerServiceRequestId);
            if (!aodOptional.isPresent()) {
               return returnResponse();
            } else {
                return new ResponseEntity<>(aodOptional, HttpStatus.OK);
            }
        } else if (serviceRequestId == ServiceRequestIdConfig.RE_ISSUE_A_PIN){
            Optional<ReIssuePinRequest> request= reIssuePinRequestRepository.getFormFromCSR(customerServiceRequestId);
            if (!request.isPresent()){
                return returnResponse();
            } else {
                return new ResponseEntity<>(request, HttpStatus.OK);
            }
        } else if(serviceRequestId == ServiceRequestIdConfig.SUBSCRIBE_TO_SMS_ALERTS_FOR_CARD_TRANSACTIONS){
            Optional<SmsSubscription> subscription=smsSubscriptionRepository.getFormFromCSR(customerServiceRequestId);
            if (!subscription.isPresent()){
                return returnResponse();
            } else {
                return new ResponseEntity<>(subscription, HttpStatus.OK);
            }
        } else if (serviceRequestId == ServiceRequestIdConfig.INCREASE_POS_LIMIT_OF_DEBIT_CARD){
            Optional<PosLimit> pos=posLimitRepository.getFormFromCSR(customerServiceRequestId);
            if (!pos.isPresent()){
                return returnResponse();
            } else {
                return new ResponseEntity<>(pos,HttpStatus.OK);
            }
        } else if (serviceRequestId == ServiceRequestIdConfig.LINK_NEW_ACCAUNTS_TO_D13EBIT_ATM_CARD){
            Optional<LinkedAccount> account=linkedAccountRepository.getFormFromCSR(customerServiceRequestId);
            if (!account.isPresent()){
                return returnResponse();
            } else {
                return new ResponseEntity<>(account,HttpStatus.OK);
            }
        } else if (serviceRequestId == ServiceRequestIdConfig.CHANGE_PRIMARY_ACCOUNT) {
            Optional<ChangePrimaryAccount> primaryAccount=changePrimaryAccountRepository.getFormFromCSR(customerServiceRequestId);
            if (!primaryAccount.isPresent()){
               return returnResponse();
            } else{
                return new ResponseEntity<>(primaryAccount,HttpStatus.OK);
            }
        } else if (serviceRequestId == ServiceRequestIdConfig.CHANGE_MAILING_ADDRESS){
            Optional<ChangeMailingMailModel> changeMailingMailOpt = changeMailingMailModelRepository.getFormFromCSR(customerServiceRequestId);
            if(!changeMailingMailOpt.isPresent()) {
                return returnResponse();
            } else {
                return new ResponseEntity<>(changeMailingMailOpt,HttpStatus.OK);
            }
        } else if (serviceRequestId == ServiceRequestIdConfig.CHANGE_PERMENT_ADDRESS){
            Optional<ChangePermanentMail> changePermanentMailOpt = changePermanentMailRepository.getFormFromCSR(customerServiceRequestId);
            if(!changePermanentMailOpt.isPresent()) {
              return returnResponse();
            } else {
                return new ResponseEntity<>(changePermanentMailOpt,HttpStatus.OK);
            }
        } else if (serviceRequestId == ServiceRequestIdConfig.REISSUE_LOGIN_PASSWORD){
            Optional<ReissueLoginPasswordModel> reissueLoginPasswordOpt = loginPasswordModelRepository.getFormFromCSR(customerServiceRequestId);
            if(!reissueLoginPasswordOpt.isPresent()) {
               return returnResponse();
            } else {
                return new ResponseEntity<>(reissueLoginPasswordOpt,HttpStatus.OK);
            }
        } else if (serviceRequestId == ServiceRequestIdConfig.LINK_FOLLOWING_JOINT_ACCOUNTS){
            Optional<LinkAccountModel> linkAccountOPT = linkAccountModelRepository.getFormFromCSR(customerServiceRequestId);
            if(!linkAccountOPT.isPresent()) {
               return returnResponse();
            } else {
                return new ResponseEntity<>(linkAccountOPT,HttpStatus.OK);
            }
        } else if (serviceRequestId == ServiceRequestIdConfig.EXCLUDE_ACCOUNTS_FROM_INTERNET_BANKING_FACILITY) {
            Optional<ExcludeInternetAccount> excludeAccountOPT = excludeInternetAccountRepository.getFormFromCSR(customerServiceRequestId);
            if(excludeAccountOPT.isPresent()) {
               return returnResponse();
            } else {
                return new ResponseEntity<>(excludeAccountOPT,HttpStatus.OK);
            }
        } else if (serviceRequestId == ServiceRequestIdConfig.OTHER_INTERNET_BANKING_SERVICES){
            Optional<InternetBanking> internetBankingOpt = internetBankingRepository.getFormFromCSR(customerServiceRequestId);
            if(!internetBankingOpt.isPresent()) {
                return returnResponse();
            } else {
                return new ResponseEntity<>(internetBankingOpt,HttpStatus.OK);
            }
        } else if(serviceRequestId == ServiceRequestIdConfig.SUBSCRIBE_TO_SMS_ALERT_CREDIT_CARD) {
            Optional<SMSAlertsForCreditCard> smsAlertForCreditCardOpt = AlertsForCreditCardRepository.getFormFromCSR(customerServiceRequestId);
            if(!smsAlertForCreditCardOpt.isPresent()) {
               return returnResponse();
            } else {
                return new ResponseEntity<>(smsAlertForCreditCardOpt,HttpStatus.OK);
            }
        } else if (serviceRequestId == ServiceRequestIdConfig.CHANGE_NIC_PASPORT_NO) {
            Optional<IdentificationForm> changeNicPassportOpt=changeIdentificationFormRepository.getFormFromCSR(customerServiceRequestId);
            if (!changeNicPassportOpt.isPresent()){
                return returnResponse();
            } else {
                return new ResponseEntity<>(changeNicPassportOpt,HttpStatus.OK);
            }

        } else if(serviceRequestId == ServiceRequestIdConfig.CHANGE_OF_TELEPHONE_NO){
            Optional<ContactDetails> request=contactDetailsRepository.getFormFromCSR(customerServiceRequestId);
            if (!request.isPresent()){
                return returnResponse();
            } else {
                return new ResponseEntity<>(request,HttpStatus.OK);
            }
        } else if (serviceRequestId == ServiceRequestIdConfig.ISSUE_ACCAUNT_STATEMENT_FOR_PERIOD){
            Optional<AccountStatementIssueRequest> accountStatementIssueRequestOpt=accountStatementIssueRequestRepository.getFormFromCSR(customerServiceRequestId);
            if (!accountStatementIssueRequestOpt.isPresent()){
               return returnResponse();
            } else {
                return new ResponseEntity<>(accountStatementIssueRequestOpt,HttpStatus.OK);
            }

        } else if (serviceRequestId == ServiceRequestIdConfig.PASSBOOK_DUPLICATE_PASSBOOK_REQUEST) {
            Optional<DuplicatePassBookRequest> bookRequestOpt = duplicatePassBookRequestRepository.getFormFromCSR(customerServiceRequestId);
            if (!bookRequestOpt.isPresent()) {
                return returnResponse();
            } else {
                return new ResponseEntity<>(bookRequestOpt,HttpStatus.OK);
            }
        } else if(serviceRequestId == ServiceRequestIdConfig.PI_ACTIVE_CACEL_ESTATEMENT_FACILITY_FOR_ACCOUNTS){
            Optional<EstatementFacility> estatementFacilityOpt = estatementFacilityRepository.getFormFromCSR(customerServiceRequestId);
            if (!estatementFacilityOpt.isPresent()){
                return returnResponse();
            } else {
                return new ResponseEntity<>(estatementFacilityOpt,HttpStatus.OK);
            }
        } else if (serviceRequestId == ServiceRequestIdConfig.CHANGE_STATEMENT_FREQUENCY_TO){
            Optional<StatementFrequency> statementFrequencyOpt = statementFrequencyRepository.getFormFromCSR(customerServiceRequestId);
            if(!statementFrequencyOpt.isPresent()){
                return returnResponse();
            } else {
                return new ResponseEntity<>(statementFrequencyOpt,HttpStatus.OK);
            }
        } else if (serviceRequestId == ServiceRequestIdConfig.WITHHOLDING_TAX_DEDUCTION_CERTIFICATE){
            Optional<WithholdingFdCd> fdCdNumbersOptional=withholdingFdCdRepository.findByRequestId(customerServiceRequestId);
            if (!fdCdNumbersOptional.isPresent()){
               return returnResponse();
            } else {
                return new ResponseEntity<>(fdCdNumbersOptional,HttpStatus.OK);
            }
        } else if (serviceRequestId == ServiceRequestIdConfig.OTHER_FD_CD_RELATED_REQUESTS){
            Optional<OtherFdCdRelatedRequest> otherFdCdRelatedRequestOptional=otherFdCdRelatedRequestRepository.findByRequestId(customerServiceRequestId);
            if (!otherFdCdRelatedRequestOptional.isPresent()){
                return returnResponse();
            } else {
                return new ResponseEntity<>(otherFdCdRelatedRequestOptional,HttpStatus.OK);
            }
        } else if (serviceRequestId == ServiceRequestIdConfig.DUPLICATE_FD_CD_CERTIFICATE) {
            Optional<DuplicateFdCdCert> duplicateFdCdCertOptional=duplicateFdCdCertRepository.findByRequestId(customerServiceRequestId);
            if (!duplicateFdCdCertOptional.isPresent()){
               return  returnResponse();
            } else {
                return new ResponseEntity<>(duplicateFdCdCertOptional,HttpStatus.OK);
            }
        } else if (serviceRequestId == ServiceRequestIdConfig.OTHER) {
            Optional<OtherServiceRequest> otherFdCdRelatedRequestOptional=otherServiceRequestRepository.findByRequestId(customerServiceRequestId);
            if (!otherFdCdRelatedRequestOptional.isPresent()){
                return returnResponse();
            } else {
                return new ResponseEntity<>(otherFdCdRelatedRequestOptional,HttpStatus.OK);
            }
        } else if(serviceRequestId == ServiceRequestIdConfig.STOP_REVOKE_PAYMENT){
            Optional<EffectOrRevokePayment> effectOrRevokePaymentOptional = effectOrRevokePaymentRepository.getFormFromCSR(customerServiceRequestId);
            if(!effectOrRevokePaymentOptional.isPresent()){
                return returnResponse();
            } else {
                return new ResponseEntity<>(effectOrRevokePaymentOptional,HttpStatus.OK);
            }
        }
            responsemodel.setMessage("Invalied Request");
            responsemodel.setStatus(false);
            return new ResponseEntity<>(responsemodel, HttpStatus.OK);

    }



    @Override
    public ResponseEntity<?> saveSignature(SignatureDTO signatureDTO) {
        ResponseModel res = new ResponseModel();
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

    @Override
    public ResponseEntity<?> rejectCustomerServiceRequest(int requestId){
       Optional<CustomerServiceRequest> customerServiceRequestOpt = customerServiceRequestRepository.findById(requestId);
       CustomerServiceRequest customerServiceRequest = customerServiceRequestOpt.get();
       customerServiceRequest.setSoftReject(true);
       customerServiceRequest.setRequestCompleteDate(java.util.Calendar.getInstance().getTime());
       customerServiceRequest.setStatus(true);
       customerServiceRequest = customerServiceRequestRepository.save(customerServiceRequest);
       int serviceRequestID = customerServiceRequest.getServiceRequest().getServiceRequestId();
       int customerRequestId = customerServiceRequest.getCustomerServiceRequestId();
       return setSoftReject(serviceRequestID,customerRequestId,customerServiceRequest);
    }

    @Override
    public ResponseEntity<?> getCustomerDetailsByDate(String date){
        ResponseModel responsemodel = new ResponseModel();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date requestDate;
        List<CustomerServiceRequest> customerServiceRequests = new ArrayList<>();
        try {
            requestDate = df.parse(date);
            List<Customer> customers = customerRepository.getCustomerRequestDetails(requestDate);
            return new ResponseEntity<>(customers,HttpStatus.OK);

        } catch (ParseException e) {
            // TODO Auto-generated catch block
            responsemodel.setStatus(false);
            responsemodel.setMessage("pass the date in a correct format");
            return new ResponseEntity<>(responsemodel,HttpStatus.BAD_REQUEST);
        }
    }





    @Override
    public ResponseEntity<?> getFIleTypes(int customerRequestId) {

        Optional<Customer> customer = customerRepository.findById(customerRequestId);

        List<ServiceRequest> serviceRequests = new ArrayList<>();

        if(customer.isPresent()) {
            List<CustomerServiceRequest> customerSserviceRequests  = customer.get().getCustomerServiceRequests();
            for(CustomerServiceRequest customerServiceRequest : customerSserviceRequests) {
                ServiceRequest serviceRequest = new ServiceRequest();
                serviceRequest = customerServiceRequest.getServiceRequest();
                serviceRequests.add(serviceRequest);
            }
        } else {
            ResponseModel responseModel = new ResponseModel();
            responseModel.setMessage("Invalied customer request id");
            responseModel.setStatus(false);
            return new ResponseEntity<>(responseModel,HttpStatus.BAD_REQUEST);
        }

        List<DocumentDTO> documentNames = new ArrayList<>();

        for(ServiceRequest serviceRequest : serviceRequests){
            String doc = getFormTipes(serviceRequest.getDigiFormId());
            if(doc != null){
                DocumentDTO documentDTO = new DocumentDTO();
                documentDTO.setDigiformId(serviceRequest.getDigiFormId());
                documentDTO.setDocument(doc);
                documentNames.add(documentDTO);
            }
        }

        if(documentNames.isEmpty()){
            return new ResponseEntity<>("No Documents to display",HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(documentNames,HttpStatus.OK);
        }

    }

    private ResponseEntity<?> invaliedRequest() {
        ResponseModel responseModel = new ResponseModel();
        responseModel.setMessage("Invalid requestId");
        responseModel.setStatus(false);
        return new ResponseEntity<>(responseModel,HttpStatus.BAD_REQUEST);
    }

    private ResponseEntity<?> setSoftReject(int serviceRequestId,int customerServiceRequestId,CustomerServiceRequest customerServiceRequest){
        if (serviceRequestId == ServiceRequestIdConfig.CARD_REQUEST) {
            Optional<AtmOrDebitCardRequest> aodOptional = atmOrDebitCardRequestRepository.getFormFromCSR(customerServiceRequestId);
            if (!aodOptional.isPresent()) {
                return new ResponseEntity<>(invaliedRequest(),HttpStatus.BAD_REQUEST);
            } else {
                AtmOrDebitCardRequest atmOrDebitCardRequest = aodOptional.get();
                atmOrDebitCardRequest.setSoftReject(true);
                atmOrDebitCardRequest.setCustomerServiceRequest(customerServiceRequest);
                atmOrDebitCardRequest = atmOrDebitCardRequestRepository.save(atmOrDebitCardRequest);
                return new ResponseEntity<>(atmOrDebitCardRequest, HttpStatus.OK);
            }
        } else if (serviceRequestId == ServiceRequestIdConfig.RE_ISSUE_A_PIN){
            Optional<ReIssuePinRequest> request= reIssuePinRequestRepository.getFormFromCSR(customerServiceRequestId);
            if (!request.isPresent()){
                return new ResponseEntity<>(invaliedRequest(),HttpStatus.BAD_REQUEST);
            } else {
                ReIssuePinRequest reIssuePinRequest= request.get();
                reIssuePinRequest.setSoftReject(true);
                reIssuePinRequest.setCustomerServiceRequest(customerServiceRequest);
                reIssuePinRequest = reIssuePinRequestRepository.save(reIssuePinRequest);
                return new ResponseEntity<>(reIssuePinRequest,HttpStatus.OK);
            }
        } else if(serviceRequestId == ServiceRequestIdConfig.SUBSCRIBE_TO_SMS_ALERTS_FOR_CARD_TRANSACTIONS){
            Optional<SmsSubscription> subscription=smsSubscriptionRepository.getFormFromCSR(customerServiceRequestId);
            if (!subscription.isPresent()){
                return new ResponseEntity<>(invaliedRequest(),HttpStatus.BAD_REQUEST);
            } else {
                SmsSubscription  smsSubscription = subscription.get();
                smsSubscription.setSoftReject(true);
                smsSubscription.setCustomerServiceRequest(customerServiceRequest);
                smsSubscription = smsSubscriptionRepository.save(smsSubscription);
                return new ResponseEntity<>(smsSubscription,HttpStatus.OK);
            }
        } else if (serviceRequestId == ServiceRequestIdConfig.INCREASE_POS_LIMIT_OF_DEBIT_CARD){
            Optional<PosLimit> pos=posLimitRepository.getFormFromCSR(customerServiceRequestId);
            if (!pos.isPresent()){
                return new ResponseEntity<>(invaliedRequest(),HttpStatus.BAD_REQUEST);
            } else {
                PosLimit posLimit = pos.get();
                posLimit.setSoftReject(true);
                posLimit.setCustomerServiceRequest(customerServiceRequest);
                posLimit = posLimitRepository.save(posLimit);
                return new ResponseEntity<>(posLimit,HttpStatus.OK);
            }
        } else if (serviceRequestId == ServiceRequestIdConfig.LINK_NEW_ACCAUNTS_TO_D13EBIT_ATM_CARD){
            Optional<LinkedAccount> account=linkedAccountRepository.getFormFromCSR(customerServiceRequestId);
            if (!account.isPresent()){
                return new ResponseEntity<>(invaliedRequest(),HttpStatus.BAD_REQUEST);
            } else {
                LinkedAccount linkedAccount = account.get();
                linkedAccount.setSoftReject(true);
                linkedAccount.setCustomerServiceRequest(customerServiceRequest);
                linkedAccount = linkedAccountRepository.save(linkedAccount);
                return new ResponseEntity<>(linkedAccount, HttpStatus.OK);
            }
        } else if (serviceRequestId == ServiceRequestIdConfig.CHANGE_PRIMARY_ACCOUNT) {
            Optional<ChangePrimaryAccount> primaryAccount=changePrimaryAccountRepository.getFormFromCSR(customerServiceRequestId);
            if (!primaryAccount.isPresent()){
                return new ResponseEntity<>(invaliedRequest(),HttpStatus.BAD_REQUEST);
            } else{
                ChangePrimaryAccount changePrimaryAccount = primaryAccount.get();
                changePrimaryAccount.setSoftReject(false);
                changePrimaryAccount.setCustomerServiceRequest(customerServiceRequest);
                changePrimaryAccount = changePrimaryAccountRepository.save(changePrimaryAccount);
                return new ResponseEntity<>(changePrimaryAccount,HttpStatus.OK);
            }
        } else if (serviceRequestId == ServiceRequestIdConfig.CHANGE_MAILING_ADDRESS){
            Optional<ChangeMailingMailModel> changeMailingMailOpt = changeMailingMailModelRepository.getFormFromCSR(customerServiceRequestId);
            if(!changeMailingMailOpt.isPresent()) {
                return new ResponseEntity<>(invaliedRequest(),HttpStatus.BAD_REQUEST);
            } else {
                ChangeMailingMailModel changeMailingMailModel = changeMailingMailOpt.get();
                changeMailingMailModel.setSoftReject(true);
                changeMailingMailModel.setCustomerServiceRequest(customerServiceRequest);
                changeMailingMailModel = changeMailingMailModelRepository.save(changeMailingMailModel);
                return new ResponseEntity<>(changeMailingMailModel,HttpStatus.OK);
            }
        } else if (serviceRequestId == ServiceRequestIdConfig.CHANGE_PERMENT_ADDRESS){
            Optional<ChangePermanentMail> changePermanentMailOpt = changePermanentMailRepository.getFormFromCSR(customerServiceRequestId);
            if(!changePermanentMailOpt.isPresent()) {
                return new ResponseEntity<>(invaliedRequest(),HttpStatus.BAD_REQUEST);
            } else {
                ChangePermanentMail changePermanentMail = changePermanentMailOpt.get();
                changePermanentMail.setSoftReject(true);
                changePermanentMail.setCustomerServiceRequest(customerServiceRequest);
                changePermanentMail = changePermanentMailRepository.save(changePermanentMail);
                return new ResponseEntity<>(changePermanentMail,HttpStatus.OK);
            }
        } else if (serviceRequestId == ServiceRequestIdConfig.REISSUE_LOGIN_PASSWORD){
            Optional<ReissueLoginPasswordModel> reissueLoginPasswordOpt = loginPasswordModelRepository.getFormFromCSR(customerServiceRequestId);
            if(!reissueLoginPasswordOpt.isPresent()) {
                return new ResponseEntity<>(invaliedRequest(),HttpStatus.BAD_REQUEST);
            } else {
                ReissueLoginPasswordModel reissueLoginPasswordModel = reissueLoginPasswordOpt.get();
                reissueLoginPasswordModel.setSoftReject(true);
                reissueLoginPasswordModel.setCustomerServiceRequest(customerServiceRequest);
                reissueLoginPasswordModel = loginPasswordModelRepository.save(reissueLoginPasswordModel);
                return new ResponseEntity<>(reissueLoginPasswordModel,HttpStatus.OK);

            }
        } else if (serviceRequestId == ServiceRequestIdConfig.LINK_FOLLOWING_JOINT_ACCOUNTS){
            Optional<LinkAccountModel> linkAccountOPT = linkAccountModelRepository.getFormFromCSR(customerServiceRequestId);
            if(!linkAccountOPT.isPresent()) {
                return new ResponseEntity<>(invaliedRequest(),HttpStatus.BAD_REQUEST);
            } else {
                LinkAccountModel linkAccountModel = new LinkAccountModel();
                linkAccountModel.setSoftReject(true);
                linkAccountModel.setCustomerServiceRequest(customerServiceRequest);
                linkAccountModel = linkAccountModelRepository.save(linkAccountModel);
                return new ResponseEntity<>(linkAccountModel,HttpStatus.OK);
            }
        } else if (serviceRequestId == ServiceRequestIdConfig.EXCLUDE_ACCOUNTS_FROM_INTERNET_BANKING_FACILITY) {
            Optional<ExcludeInternetAccount> excludeAccountOPT = excludeInternetAccountRepository.getFormFromCSR(customerServiceRequestId);
            if(excludeAccountOPT.isPresent()) {
                return new ResponseEntity<>(invaliedRequest(),HttpStatus.BAD_REQUEST);
            } else {
                ExcludeInternetAccount excludeInternetAccount = excludeAccountOPT.get();
                excludeInternetAccount.setSoftReject(true);
                excludeInternetAccount.setCustomerServiceRequest(customerServiceRequest);
                excludeInternetAccount = excludeInternetAccountRepository.save(excludeInternetAccount);
                return new ResponseEntity<>(excludeInternetAccount,HttpStatus.OK);
            }
        } else if (serviceRequestId == ServiceRequestIdConfig.OTHER_INTERNET_BANKING_SERVICES){
            Optional<InternetBanking> internetBankingOpt = internetBankingRepository.getFormFromCSR(customerServiceRequestId);
            if(!internetBankingOpt.isPresent()) {
                return new ResponseEntity<>(invaliedRequest(),HttpStatus.BAD_REQUEST);
            } else {
                InternetBanking internetBanking = internetBankingOpt.get();
                internetBanking.setSoftReject(true);
                internetBanking.setCustomerServiceRequest(customerServiceRequest);
                internetBanking = internetBankingRepository.save(internetBanking);
                return new ResponseEntity<>(internetBanking,HttpStatus.OK);
            }
        } else if(serviceRequestId == ServiceRequestIdConfig.SUBSCRIBE_TO_SMS_ALERT_CREDIT_CARD) {
            Optional<SMSAlertsForCreditCard> smsAlertForCreditCardOpt = AlertsForCreditCardRepository.getFormFromCSR(customerServiceRequestId);
            if(!smsAlertForCreditCardOpt.isPresent()) {
                return new ResponseEntity<>(invaliedRequest(),HttpStatus.BAD_REQUEST);
            } else {
                SMSAlertsForCreditCard smsAlertsForCreditCard = smsAlertForCreditCardOpt.get();
                smsAlertsForCreditCard.setSoftReject(true);
                smsAlertsForCreditCard.setCustomerServiceRequest(customerServiceRequest);
                smsAlertsForCreditCard = AlertsForCreditCardRepository.save(smsAlertsForCreditCard);
                return new ResponseEntity<>(smsAlertsForCreditCard,HttpStatus.OK);
            }
        } else if (serviceRequestId == ServiceRequestIdConfig.CHANGE_NIC_PASPORT_NO) {
            Optional<IdentificationForm> changeNicPassportOpt=changeIdentificationFormRepository.getFormFromCSR(customerServiceRequestId);
            if (!changeNicPassportOpt.isPresent()){
                return new ResponseEntity<>(invaliedRequest(),HttpStatus.BAD_REQUEST);
            } else {
                IdentificationForm identificationForm = changeNicPassportOpt.get();
                identificationForm.setSoftReject(true);
                identificationForm.setCustomerServiceRequest(customerServiceRequest);
                identificationForm = changeIdentificationFormRepository.save(identificationForm);
                return new ResponseEntity<>(identificationForm,HttpStatus.OK);
            }

        } else if(serviceRequestId == ServiceRequestIdConfig.CHANGE_OF_TELEPHONE_NO){
            Optional<ContactDetails> request=contactDetailsRepository.getFormFromCSR(customerServiceRequestId);
            if (!request.isPresent()){
                return new ResponseEntity<>(invaliedRequest(),HttpStatus.BAD_REQUEST);
            } else {
                ContactDetails contactDetails = request.get();
                contactDetails.setSoftReject(true);
                contactDetails.setCustomerServiceRequest(customerServiceRequest);
                contactDetails = contactDetailsRepository.save(contactDetails);
                return new ResponseEntity<>(contactDetails,HttpStatus.OK);
            }
        } else if (serviceRequestId == ServiceRequestIdConfig.ISSUE_ACCAUNT_STATEMENT_FOR_PERIOD){
            Optional<AccountStatementIssueRequest> accountStatementIssueRequestOpt=accountStatementIssueRequestRepository.getFormFromCSR(customerServiceRequestId);
            if (!accountStatementIssueRequestOpt.isPresent()){
                return new ResponseEntity<>(invaliedRequest(),HttpStatus.BAD_REQUEST);
            } else {
                AccountStatementIssueRequest accountStatementIssueRequest = accountStatementIssueRequestOpt.get();
                accountStatementIssueRequest.setSoftReject(true);
                accountStatementIssueRequest.setCustomerServiceRequest(customerServiceRequest);
                accountStatementIssueRequest = accountStatementIssueRequestRepository.save(accountStatementIssueRequest);
                return new ResponseEntity<>(accountStatementIssueRequest,HttpStatus.OK);
            }

        } else if (serviceRequestId == ServiceRequestIdConfig.PASSBOOK_DUPLICATE_PASSBOOK_REQUEST) {
            Optional<DuplicatePassBookRequest> bookRequestOpt = duplicatePassBookRequestRepository.getFormFromCSR(customerServiceRequestId);
            if (!bookRequestOpt.isPresent()) {
                return new ResponseEntity<>(invaliedRequest(),HttpStatus.BAD_REQUEST);
            } else {
                DuplicatePassBookRequest duplicatePassBookRequest= bookRequestOpt.get();
                duplicatePassBookRequest.setSoftReject(true);
                duplicatePassBookRequest.setCustomerServiceRequest(customerServiceRequest);
                duplicatePassBookRequest = duplicatePassBookRequestRepository.save(duplicatePassBookRequest);
                return new ResponseEntity<>(duplicatePassBookRequest,HttpStatus.OK);
            }
        } else if(serviceRequestId == ServiceRequestIdConfig.PI_ACTIVE_CACEL_ESTATEMENT_FACILITY_FOR_ACCOUNTS){
            Optional<EstatementFacility> estatementFacilityOpt = estatementFacilityRepository.getFormFromCSR(customerServiceRequestId);
            if (!estatementFacilityOpt.isPresent()){
                return new ResponseEntity<>(invaliedRequest(),HttpStatus.BAD_REQUEST);
            } else {
                EstatementFacility estatementFacility = estatementFacilityOpt.get();
                estatementFacility.setSoftReject(true);
                estatementFacility.setCustomerServiceRequest(customerServiceRequest);
                estatementFacility = estatementFacilityRepository.save(estatementFacility);
                return new ResponseEntity<>(estatementFacility,HttpStatus.OK);
            }
        } else if (serviceRequestId == ServiceRequestIdConfig.CHANGE_STATEMENT_FREQUENCY_TO){
            Optional<StatementFrequency> statementFrequencyOpt = statementFrequencyRepository.getFormFromCSR(customerServiceRequestId);
            if(!statementFrequencyOpt.isPresent()){
                return new ResponseEntity<>(invaliedRequest(),HttpStatus.BAD_REQUEST);
            } else {
                StatementFrequency statementFrequency = statementFrequencyOpt.get();
                statementFrequency.setSoftReject(true);
                statementFrequency.setCustomerServiceRequest(customerServiceRequest);
                statementFrequency = statementFrequencyRepository.save(statementFrequency);
                return new ResponseEntity<>(statementFrequency,HttpStatus.OK);
            }
        } else if (serviceRequestId == ServiceRequestIdConfig.WITHHOLDING_TAX_DEDUCTION_CERTIFICATE){
            Optional<WithholdingFdCd> fdCdNumbersOptional=withholdingFdCdRepository.findByRequestId(customerServiceRequestId);
            if (!fdCdNumbersOptional.isPresent()){
                return new ResponseEntity<>(invaliedRequest(),HttpStatus.BAD_REQUEST);
            } else {
                WithholdingFdCd withholdingFdCd = fdCdNumbersOptional.get();
                withholdingFdCd.setSoftReject(true);
                withholdingFdCd.setCustomerServiceRequest(customerServiceRequest);
                withholdingFdCd = withholdingFdCdRepository.save(withholdingFdCd);
                return new ResponseEntity<>(withholdingFdCd,HttpStatus.OK);
            }
        } else if (serviceRequestId == ServiceRequestIdConfig.OTHER_FD_CD_RELATED_REQUESTS){
            Optional<OtherFdCdRelatedRequest> otherFdCdRelatedRequestOptional=otherFdCdRelatedRequestRepository.findByRequestId(customerServiceRequestId);
            if (!otherFdCdRelatedRequestOptional.isPresent()){
                return new ResponseEntity<>(invaliedRequest(),HttpStatus.BAD_REQUEST);
            } else {
                OtherFdCdRelatedRequest otherFdCdRelatedRequest= otherFdCdRelatedRequestOptional.get();
                otherFdCdRelatedRequest.setSoftReject(true);
                otherFdCdRelatedRequest.setCustomerServiceRequest(customerServiceRequest);
                otherFdCdRelatedRequest = otherFdCdRelatedRequestRepository.save(otherFdCdRelatedRequest);
                return new ResponseEntity<>(otherFdCdRelatedRequest,HttpStatus.OK);
            }
        } else if (serviceRequestId == ServiceRequestIdConfig.DUPLICATE_FD_CD_CERTIFICATE) {
            Optional<DuplicateFdCdCert> duplicateFdCdCertOptional=duplicateFdCdCertRepository.findByRequestId(customerServiceRequestId);
            if (!duplicateFdCdCertOptional.isPresent()){
                return new ResponseEntity<>(invaliedRequest(),HttpStatus.BAD_REQUEST);
            } else {
                DuplicateFdCdCert duplicateFdCdCert = duplicateFdCdCertOptional.get();
                duplicateFdCdCert.setSoftReject(true);
                duplicateFdCdCert.setCustomerServiceRequest(customerServiceRequest);
                duplicateFdCdCert = duplicateFdCdCertRepository.save(duplicateFdCdCert);
                return new ResponseEntity<>(duplicateFdCdCert,HttpStatus.OK);
            }
        } else if (serviceRequestId == ServiceRequestIdConfig.OTHER) {
            Optional<OtherServiceRequest> otherServiceRequest=otherServiceRequestRepository.findByRequestId(customerServiceRequestId);
            if (!otherServiceRequest.isPresent()){
                return new ResponseEntity<>(invaliedRequest(),HttpStatus.BAD_REQUEST);
            } else {
                OtherServiceRequest otherServiceRequest1 = otherServiceRequest.get();
                otherServiceRequest1.setSoftReject(true);
                otherServiceRequest1.setCustomerServiceRequest(customerServiceRequest);
                otherServiceRequest1 = otherServiceRequestRepository.save(otherServiceRequest1);
                return new ResponseEntity<>(otherServiceRequest1,HttpStatus.OK);
            }
        } else if(serviceRequestId == ServiceRequestIdConfig.STOP_REVOKE_PAYMENT){
            Optional<EffectOrRevokePayment> effectOrRevokePaymentOptional = effectOrRevokePaymentRepository.getFormFromCSR(customerServiceRequestId);
            if(!effectOrRevokePaymentOptional.isPresent()){
                return new ResponseEntity<>(invaliedRequest(),HttpStatus.BAD_REQUEST);
            } else {
                EffectOrRevokePayment effectOrRevokePayment = effectOrRevokePaymentOptional.get();
                effectOrRevokePayment.setSoftReject(true);
                effectOrRevokePayment.setCustomerServiceRequest(customerServiceRequest);
                effectOrRevokePayment = effectOrRevokePaymentRepository.save(effectOrRevokePayment);
                return new ResponseEntity<>(effectOrRevokePayment,HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(invaliedRequest(),HttpStatus.BAD_REQUEST);
    }



    private ResponseEntity<?> returnResponse(){
        ResponseModel responsemodel = new ResponseModel();
        responsemodel.setMessage("Customer Havent fill the form yet");
        responsemodel.setStatus(false);
        return new ResponseEntity<>(responsemodel, HttpStatus.NO_CONTENT);
    }

    private boolean checkFormexists(int serviceRequestId,int customerServiceRequestId ){
        if (serviceRequestId == ServiceRequestIdConfig.CARD_REQUEST) {
            Optional<AtmOrDebitCardRequest> aodOptional = atmOrDebitCardRequestRepository.getFormFromCSR(customerServiceRequestId);
            if (!aodOptional.isPresent()) {
                return false;
            } else {
                return true;
            }
        } else if (serviceRequestId == ServiceRequestIdConfig.RE_ISSUE_A_PIN){
            Optional<ReIssuePinRequest> request= reIssuePinRequestRepository.getFormFromCSR(customerServiceRequestId);
            if (!request.isPresent()){
                return false;
            } else {
                return true;
            }
        } else if(serviceRequestId == ServiceRequestIdConfig.SUBSCRIBE_TO_SMS_ALERTS_FOR_CARD_TRANSACTIONS){
            Optional<SmsSubscription> subscription=smsSubscriptionRepository.getFormFromCSR(customerServiceRequestId);
            if (!subscription.isPresent()){
                return false;
            } else {
                return true;
            }
        } else if (serviceRequestId == ServiceRequestIdConfig.INCREASE_POS_LIMIT_OF_DEBIT_CARD){
            Optional<PosLimit> pos=posLimitRepository.getFormFromCSR(customerServiceRequestId);
            if (!pos.isPresent()){
                return false;
            } else {
                return true;
            }
        } else if (serviceRequestId == ServiceRequestIdConfig.LINK_NEW_ACCAUNTS_TO_D13EBIT_ATM_CARD){
            Optional<LinkedAccount> account=linkedAccountRepository.getFormFromCSR(customerServiceRequestId);
            if (!account.isPresent()){
                return false;
            } else {
                return true;
            }
        } else if (serviceRequestId == ServiceRequestIdConfig.CHANGE_PRIMARY_ACCOUNT) {
            Optional<ChangePrimaryAccount> primaryAccount=changePrimaryAccountRepository.getFormFromCSR(customerServiceRequestId);
            if (!primaryAccount.isPresent()){
                return false;
            } else{
                return true;
            }
        } else if (serviceRequestId == ServiceRequestIdConfig.CHANGE_MAILING_ADDRESS){
            Optional<ChangeMailingMailModel> changeMailingMailOpt = changeMailingMailModelRepository.getFormFromCSR(customerServiceRequestId);
            if(!changeMailingMailOpt.isPresent()) {
                return false;
            } else {
                return true;
            }
        } else if (serviceRequestId == ServiceRequestIdConfig.CHANGE_PERMENT_ADDRESS){
            Optional<ChangePermanentMail> changePermanentMailOpt = changePermanentMailRepository.getFormFromCSR(customerServiceRequestId);
            if(!changePermanentMailOpt.isPresent()) {
                return false;
            } else {
                return true;
            }
        } else if (serviceRequestId == ServiceRequestIdConfig.REISSUE_LOGIN_PASSWORD){
            Optional<ReissueLoginPasswordModel> reissueLoginPasswordOpt = loginPasswordModelRepository.getFormFromCSR(customerServiceRequestId);
            if(!reissueLoginPasswordOpt.isPresent()) {
                return false;
            } else {
                return true;
            }
        } else if (serviceRequestId == ServiceRequestIdConfig.LINK_FOLLOWING_JOINT_ACCOUNTS){
            Optional<LinkAccountModel> linkAccountOPT = linkAccountModelRepository.getFormFromCSR(customerServiceRequestId);
            if(!linkAccountOPT.isPresent()) {
                return false;
            } else {
                return true;
            }
        } else if (serviceRequestId == ServiceRequestIdConfig.EXCLUDE_ACCOUNTS_FROM_INTERNET_BANKING_FACILITY) {
            Optional<ExcludeInternetAccount> excludeAccountOPT = excludeInternetAccountRepository.getFormFromCSR(customerServiceRequestId);
            if(excludeAccountOPT.isPresent()) {
                return false;
            } else {
                return true;
            }
        } else if (serviceRequestId == ServiceRequestIdConfig.OTHER_INTERNET_BANKING_SERVICES){
            Optional<InternetBanking> internetBankingOpt = internetBankingRepository.getFormFromCSR(customerServiceRequestId);
            if(!internetBankingOpt.isPresent()) {
                return false;
            } else {
                return true;
            }
        } else if(serviceRequestId == ServiceRequestIdConfig.SUBSCRIBE_TO_SMS_ALERT_CREDIT_CARD) {
            Optional<SMSAlertsForCreditCard> smsAlertForCreditCardOpt = AlertsForCreditCardRepository.getFormFromCSR(customerServiceRequestId);
            if(!smsAlertForCreditCardOpt.isPresent()) {
                return false;
            } else {
                return true;
            }
        } else if (serviceRequestId == ServiceRequestIdConfig.CHANGE_NIC_PASPORT_NO) {
            Optional<IdentificationForm> changeNicPassportOpt=changeIdentificationFormRepository.getFormFromCSR(customerServiceRequestId);
            if (!changeNicPassportOpt.isPresent()){
                return false;
            } else {
                return true;
            }

        } else if(serviceRequestId == ServiceRequestIdConfig.CHANGE_OF_TELEPHONE_NO){
            Optional<ContactDetails> request=contactDetailsRepository.getFormFromCSR(customerServiceRequestId);
            if (!request.isPresent()){
                return false;
            } else {
                return true;
            }
        } else if (serviceRequestId == ServiceRequestIdConfig.ISSUE_ACCAUNT_STATEMENT_FOR_PERIOD){
            Optional<AccountStatementIssueRequest> accountStatementIssueRequestOpt=accountStatementIssueRequestRepository.getFormFromCSR(customerServiceRequestId);
            if (!accountStatementIssueRequestOpt.isPresent()){
                return false;
            } else {
                return true;
            }

        } else if (serviceRequestId == ServiceRequestIdConfig.PASSBOOK_DUPLICATE_PASSBOOK_REQUEST) {
            Optional<DuplicatePassBookRequest> bookRequestOpt = duplicatePassBookRequestRepository.getFormFromCSR(customerServiceRequestId);
            if (!bookRequestOpt.isPresent()) {
                return false;
            } else {
                return true;
            }
        } else if(serviceRequestId == ServiceRequestIdConfig.PI_ACTIVE_CACEL_ESTATEMENT_FACILITY_FOR_ACCOUNTS){
            Optional<EstatementFacility> estatementFacilityOpt = estatementFacilityRepository.getFormFromCSR(customerServiceRequestId);
            if (!estatementFacilityOpt.isPresent()){
                return false;
            } else {
                return true;
            }
        } else if (serviceRequestId == ServiceRequestIdConfig.CHANGE_STATEMENT_FREQUENCY_TO){
            Optional<StatementFrequency> statementFrequencyOpt = statementFrequencyRepository.getFormFromCSR(customerServiceRequestId);
            if(!statementFrequencyOpt.isPresent()){
                return false;
            } else {
                return true;
            }
        } else if (serviceRequestId == ServiceRequestIdConfig.WITHHOLDING_TAX_DEDUCTION_CERTIFICATE){
            Optional<WithholdingFdCd> fdCdNumbersOptional=withholdingFdCdRepository.findByRequestId(customerServiceRequestId);
            if (!fdCdNumbersOptional.isPresent()){
                return false;
            } else {
                return true;
            }
        } else if (serviceRequestId == ServiceRequestIdConfig.OTHER_FD_CD_RELATED_REQUESTS){
            Optional<OtherFdCdRelatedRequest> otherFdCdRelatedRequestOptional=otherFdCdRelatedRequestRepository.findByRequestId(customerServiceRequestId);
            if (!otherFdCdRelatedRequestOptional.isPresent()){
                return false;
            } else {
                return true;
            }
        } else if (serviceRequestId == ServiceRequestIdConfig.DUPLICATE_FD_CD_CERTIFICATE) {
            Optional<DuplicateFdCdCert> duplicateFdCdCertOptional=duplicateFdCdCertRepository.findByRequestId(customerServiceRequestId);
            if (!duplicateFdCdCertOptional.isPresent()){
                return false;
            } else {
                return true;
            }
        } else if (serviceRequestId == ServiceRequestIdConfig.OTHER) {
            Optional<OtherServiceRequest> otherFdCdRelatedRequestOptional=otherServiceRequestRepository.findByRequestId(customerServiceRequestId);
            if (!otherFdCdRelatedRequestOptional.isPresent()){
                return false;
            } else {
                return true;
            }
        } else if(serviceRequestId == ServiceRequestIdConfig.STOP_REVOKE_PAYMENT){
            Optional<EffectOrRevokePayment> effectOrRevokePaymentOptional = effectOrRevokePaymentRepository.getFormFromCSR(customerServiceRequestId);
            if(!effectOrRevokePaymentOptional.isPresent()){
                return false;
            } else {
                return true;
            }
        }
        return false;
    }


    private String getFormTipes(int serviceRequestId){

        String document;

        if (serviceRequestId == ServiceRequestIdConfig.CARD_REQUEST) {
            return null;
        }else if (serviceRequestId == ServiceRequestIdConfig.RE_ISSUE_A_PIN){
            return null;
        } else if(serviceRequestId == ServiceRequestIdConfig.SUBSCRIBE_TO_SMS_ALERTS_FOR_CARD_TRANSACTIONS){
            return null;
        } else if (serviceRequestId == ServiceRequestIdConfig.INCREASE_POS_LIMIT_OF_DEBIT_CARD){
            return null;
        } else if (serviceRequestId == ServiceRequestIdConfig.LINK_NEW_ACCAUNTS_TO_D13EBIT_ATM_CARD){
            return null;
        } else if (serviceRequestId == ServiceRequestIdConfig.CHANGE_PRIMARY_ACCOUNT) {
            return null;
        } else if (serviceRequestId == ServiceRequestIdConfig.CHANGE_MAILING_ADDRESS){
            return null;
        } else if (serviceRequestId == ServiceRequestIdConfig.CHANGE_PERMENT_ADDRESS){
            return null;
        } else if (serviceRequestId == ServiceRequestIdConfig.REISSUE_LOGIN_PASSWORD){
            return null;
        } else if (serviceRequestId == ServiceRequestIdConfig.LINK_FOLLOWING_JOINT_ACCOUNTS){
            return null;
        } else if (serviceRequestId == ServiceRequestIdConfig.EXCLUDE_ACCOUNTS_FROM_INTERNET_BANKING_FACILITY) {
            return null;
        } else if (serviceRequestId == ServiceRequestIdConfig.OTHER_INTERNET_BANKING_SERVICES){
            return null;
        } else if(serviceRequestId == ServiceRequestIdConfig.SUBSCRIBE_TO_SMS_ALERT_CREDIT_CARD) {
            return null;
        } else if (serviceRequestId == ServiceRequestIdConfig.CHANGE_NIC_PASPORT_NO) {
            document = "Coppy of Nic Or Passport";
            return document;
        } else if(serviceRequestId == ServiceRequestIdConfig.CHANGE_OF_TELEPHONE_NO){
            return null;
        } else if (serviceRequestId == ServiceRequestIdConfig.ISSUE_ACCAUNT_STATEMENT_FOR_PERIOD){
            return null;
        } else if (serviceRequestId == ServiceRequestIdConfig.PASSBOOK_DUPLICATE_PASSBOOK_REQUEST) {
            document = "Indimatly duly should be submitted";
            return document;

        } else if(serviceRequestId == ServiceRequestIdConfig.PI_ACTIVE_CACEL_ESTATEMENT_FACILITY_FOR_ACCOUNTS){
            return null;
        } else if (serviceRequestId == ServiceRequestIdConfig.CHANGE_STATEMENT_FREQUENCY_TO){
            return null;
        } else if (serviceRequestId == ServiceRequestIdConfig.WITHHOLDING_TAX_DEDUCTION_CERTIFICATE){
            return null;
        } else if (serviceRequestId == ServiceRequestIdConfig.OTHER_FD_CD_RELATED_REQUESTS){
            return null;
        } else if (serviceRequestId == ServiceRequestIdConfig.DUPLICATE_FD_CD_CERTIFICATE) {
            document = "Indimatly duly should be submitted";
            return document;

        } else if (serviceRequestId == ServiceRequestIdConfig.OTHER) {
            return null;
        } else if(serviceRequestId == ServiceRequestIdConfig.STOP_REVOKE_PAYMENT){
            return null;
        }
         return null;
    }

}
