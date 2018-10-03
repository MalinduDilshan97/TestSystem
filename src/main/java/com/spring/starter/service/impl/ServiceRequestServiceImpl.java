package com.spring.starter.service.impl;

import java.security.Principal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import com.spring.starter.DTO.SignatureDTO;
import com.spring.starter.Repository.*;
import com.spring.starter.configuration.ServiceRequestIdConfig;
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
        responsemodel.setMessage("User Request updated successfully");
        responsemodel.setStatus(true);
        return new ResponseEntity<>(responsemodel, HttpStatus.CREATED);
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
        List<CustomerServiceRequest> allCustomers = customerServiceRequestRepository.getAllCustomerRequest(customerId);
        Optional<StaffUser> staffUser = staffUserRepository.findById(Integer.parseInt(principal.getName()));
        if(!staffUser.isPresent()){
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
            Optional<SmsSubscription> subscription=smsSubscriptionRepository.getFormFromCSR(serviceRequestId);
            if (!subscription.isPresent()){
                return returnResponse();
            } else {
                return new ResponseEntity<>(subscription, HttpStatus.OK);
            }
        } else if (serviceRequestId == ServiceRequestIdConfig.INCREASE_POS_LIMIT_OF_DEBIT_CARD){
            Optional<PosLimit> pos=posLimitRepository.getFormFromCSR(serviceRequestId);
            if (!pos.isPresent()){
                return returnResponse();
            } else {
                return new ResponseEntity<>(pos,HttpStatus.OK);
            }
        } else if (serviceRequestId == ServiceRequestIdConfig.LINK_NEW_ACCAUNTS_TO_D13EBIT_ATM_CARD){
            Optional<LinkedAccount> account=linkedAccountRepository.getFormFromCSR(serviceRequestId);
            if (!account.isPresent()){
                return returnResponse();
            } else {
                return new ResponseEntity<>(account,HttpStatus.OK);
            }
        } else if (serviceRequestId == ServiceRequestIdConfig.CHANGE_PRIMARY_ACCOUNT) {
            Optional<ChangePrimaryAccount> primaryAccount=changePrimaryAccountRepository.getFormFromCSR(serviceRequestId);
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
            Optional<CustomerServiceRequest> changeNicPassportOpt=customerServiceRequestRepository.findById(customerServiceRequestId);
            if (!changeNicPassportOpt.isPresent()){
                return returnResponse();
            } else {
                return new ResponseEntity<>(changeNicPassportOpt,HttpStatus.OK);
            }

        } else if(serviceRequestId == ServiceRequestIdConfig.CHANGE_OF_TELEPHONE_NO){
            Optional<ContactDetails> request=contactDetailsRepository.getFormFromCSR(serviceRequestId);
            if (request.isPresent()){
                return returnResponse();
            } else {
                return new ResponseEntity<>(contactDetailsRepository,HttpStatus.OK);
            }
        } else if (serviceRequestId == ServiceRequestIdConfig.ISSUE_ACCAUNT_STATEMENT_FOR_PERIOD){
            Optional<AccountStatementIssueRequest> accountStatementIssueRequestOpt=accountStatementIssueRequestRepository.getFormFromCSR(serviceRequestId);
            if (!accountStatementIssueRequestOpt.isPresent()){
               return returnResponse();
            } else {
                return new ResponseEntity<>(accountStatementIssueRequestOpt,HttpStatus.OK);
            }

        } else if (serviceRequestId == ServiceRequestIdConfig.PASSBOOK_DUPLICATE_PASSBOOK_REQUEST) {
            Optional<DuplicatePassBookRequest> bookRequestOpt = duplicatePassBookRequestRepository.getFormFromCSR(serviceRequestId);
            if (!bookRequestOpt.isPresent()) {
                return returnResponse();
            } else {
                return new ResponseEntity<>(bookRequestOpt,HttpStatus.OK);
            }
        } else if(serviceRequestId == ServiceRequestIdConfig.PI_ACTIVE_CACEL_ESTATEMENT_FACILITY_FOR_ACCOUNTS){
            Optional<EstatementFacility> estatementFacilityOpt = estatementFacilityRepository.getFormFromCSR(serviceRequestId);
            if (!estatementFacilityOpt.isPresent()){
                return returnResponse();
            } else {
                return new ResponseEntity<>(estatementFacilityOpt,HttpStatus.OK);
            }
        } else if (serviceRequestId == ServiceRequestIdConfig.CHANGE_STATEMENT_FREQUENCY_TO){
            Optional<StatementFrequency> statementFrequencyOpt = statementFrequencyRepository.getFormFromCSR(serviceRequestId);
            if(!statementFrequencyOpt.isPresent()){
                return returnResponse();
            } else {
                return new ResponseEntity<>(statementFrequencyOpt,HttpStatus.OK);
            }
        } else if (serviceRequestId == ServiceRequestIdConfig.WITHHOLDING_TAX_DEDUCTION_CERTIFICATE){
            Optional<WithholdingFdCd> fdCdNumbersOptional=withholdingFdCdRepository.findByRequestId(serviceRequestId);
            if (!fdCdNumbersOptional.isPresent()){
               return returnResponse();
            } else {
                return new ResponseEntity<>(fdCdNumbersOptional,HttpStatus.OK);
            }
        } else if (serviceRequestId == ServiceRequestIdConfig.OTHER_FD_CD_RELATED_REQUESTS){
            Optional<OtherFdCdRelatedRequest> otherFdCdRelatedRequestOptional=otherFdCdRelatedRequestRepository.findByRequestId(serviceRequestId);
            if (!otherFdCdRelatedRequestOptional.isPresent()){
                return returnResponse();
            } else {
                return new ResponseEntity<>(otherFdCdRelatedRequestOptional,HttpStatus.OK);
            }
        } else if (serviceRequestId == ServiceRequestIdConfig.DUPLICATE_FD_CD_CERTIFICATE) {
            Optional<DuplicateFdCdCert> duplicateFdCdCertOptional=duplicateFdCdCertRepository.findByRequestId(serviceRequestId);
            if (!duplicateFdCdCertOptional.isPresent()){
               return  returnResponse();
            } else {
                return new ResponseEntity<>(duplicateFdCdCertOptional,HttpStatus.OK);
            }
        } else if (serviceRequestId == ServiceRequestIdConfig.OTHER) {
            Optional<OtherServiceRequest> otherFdCdRelatedRequestOptional=otherServiceRequestRepository.findByRequestId(serviceRequestId);
            if (otherFdCdRelatedRequestOptional.isPresent()){
                return returnResponse();
            } else {
                return new ResponseEntity<>(otherFdCdRelatedRequestOptional,HttpStatus.OK);
            }
        } else if(serviceRequestId == ServiceRequestIdConfig.STOP_REVOKE_PAYMENT){
            /***************Fill this*************************/
        }
            responsemodel.setMessage("Invalied Request");
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

    private ResponseEntity<?> returnResponse(){
        ResponseModel responsemodel = new ResponseModel();
        responsemodel.setMessage("Customer Havent fill the form yet");
        responsemodel.setStatus(false);
        return new ResponseEntity<>(responsemodel, HttpStatus.NO_CONTENT);
    }
}
