package com.spring.starter.service.impl;

import com.spring.starter.DTO.IdentificationAddDTO;
import com.spring.starter.Repository.ChangeIdentificationFormRepository;
import com.spring.starter.Repository.CustomerServiceRequestRepository;
import com.spring.starter.service.ServiceRequestCustomerLogService;
import com.spring.starter.service.CustomerServiceRequestService;
import com.spring.starter.configuration.ServiceRequestIdConfig;
import com.spring.starter.service.ServiceRequestFormLogService;
import com.spring.starter.Repository.ContactDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import com.spring.starter.DTO.IdentificationFormDTO;
import com.spring.starter.DTO.ContactDetailsDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import javax.servlet.http.HttpServletRequest;
import com.spring.starter.util.FileStorage;
import org.springframework.http.HttpStatus;
import javax.transaction.Transactional;
import com.spring.starter.model.*;
import java.util.Optional;


@Transactional
@Service
public class CustomerServiceRequestServiceImpl implements CustomerServiceRequestService {

    @Autowired
    private ChangeIdentificationFormRepository changeIdentificationFormRepository;
    @Autowired
    private CustomerServiceRequestRepository customerServiceRequestRepository;
    @Autowired
    private ContactDetailsRepository contactDetailsRepository;
    @Autowired
    private ServiceRequestCustomerLogService serviceRequestCustomerLogService;
    @Autowired
    private ServiceRequestFormLogService serviceRequestFormLogService;
    @Autowired
    private FileStorage fileStorage;

    private ResponseModel res = new ResponseModel();
    private ServiceRequestCustomerLog serviceRequestCustomerLog = new ServiceRequestCustomerLog();
    private ServiceRequestFormLog serviceRequestFormLog = new ServiceRequestFormLog();

    @Override
    public ResponseEntity<?> changeIdentificationDetails(IdentificationFormDTO identificationFormDTO, HttpServletRequest request) {
        ResponseModel responsemodel = new ResponseModel();
        Optional<CustomerServiceRequest> optional = customerServiceRequestRepository.findById(identificationFormDTO.getCustomerServiceRequestId());

        if (!optional.isPresent()) {
            res.setMessage(" No Data Found To Complete The Request");
            res.setStatus(false);

            return new ResponseEntity<>(res, HttpStatus.NOT_FOUND);
        }
        int serviceRequestId = serviceRequestId = optional.get().getServiceRequest().getDigiFormId();
        if (serviceRequestId != ServiceRequestIdConfig.CHANGE_NIC_PASPORT_NO) {
            responsemodel.setMessage("Invalid Request");
            responsemodel.setStatus(false);
            return new ResponseEntity<>(responsemodel, HttpStatus.BAD_REQUEST);
        } else {

            CustomerServiceRequest customerServiceRequest = optional.get();


//          creating a new Path
            String location = ("/" + optional.get().getCustomerServiceRequestId() + "");
//          Saving and getting storage url
            String url = fileStorage.fileSave(identificationFormDTO.getFile(), location);
//          Checking Is File Saved ?
            if (url.equals("Failed")) {
                res.setMessage(" Failed To Upload File");
                res.setStatus(false);
                return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
            } else {

                IdentificationForm identificationForm = new IdentificationForm();

                Optional<IdentificationForm> optionalForm = changeIdentificationFormRepository.getFormFromCSR(identificationFormDTO.getCustomerServiceRequestId());
                if (optionalForm.isPresent()) {
                    identificationForm.setChangeIdentificationFormId(optionalForm.get().getChangeIdentificationFormId());
                }

                identificationForm.setIdentification(identificationFormDTO.getIdentification());
                identificationForm.setDocumentUrl(url);
                identificationForm.setCustomerServiceRequest(customerServiceRequest);

                if (changeIdentificationFormRepository.save(identificationForm) != null) {

                    serviceRequestCustomerLog.setDate(java.util.Calendar.getInstance().getTime());
                    serviceRequestCustomerLog.setIdentification(customerServiceRequest.getCustomer().getIdentification());
                    serviceRequestCustomerLog.setIp(request.getRemoteAddr());
                    serviceRequestCustomerLog.setMessage("Request Form Successfully Saved To The System");
                    boolean result = serviceRequestCustomerLogService.saveServiceRequestCustomerLog(serviceRequestCustomerLog);

                    if (result) {
                        serviceRequestFormLog.setDigiFormId(customerServiceRequest.getServiceRequest().getDigiFormId());
                        serviceRequestFormLog.setCustomerId(customerServiceRequest.getCustomer().getCustomerId());
                        serviceRequestFormLog.setDate(java.util.Calendar.getInstance().getTime());
                        serviceRequestFormLog.setFromId(customerServiceRequest.getServiceRequest().getServiceRequestId());
                        serviceRequestFormLog.setIp(request.getRemoteAddr());
                        serviceRequestFormLog.setStatus(true);
                        serviceRequestFormLog.setMessage(" Request Form Successfully Saved To The  System");
                        serviceRequestFormLogService.saveServiceRequestFormLog(serviceRequestFormLog);
                    }

                    res.setMessage(" Request Form Successfully Saved To The System");
                    res.setStatus(true);
                    return new ResponseEntity<>(res, HttpStatus.CREATED);
                } else {

                    serviceRequestCustomerLog.setDate(java.util.Calendar.getInstance().getTime());
                    serviceRequestCustomerLog.setIdentification(customerServiceRequest.getCustomer().getIdentification());
                    serviceRequestCustomerLog.setIp(request.getRemoteAddr());
                    serviceRequestCustomerLog.setMessage("Failed TO Save The Request... Operation Unsuccessful");
                    boolean result = serviceRequestCustomerLogService.saveServiceRequestCustomerLog(serviceRequestCustomerLog);

                    if (result) {
                        serviceRequestFormLog.setDigiFormId(customerServiceRequest.getServiceRequest().getDigiFormId());
                        serviceRequestFormLog.setCustomerId(customerServiceRequest.getCustomer().getCustomerId());
                        serviceRequestFormLog.setDate(java.util.Calendar.getInstance().getTime());
                        serviceRequestFormLog.setFromId(customerServiceRequest.getServiceRequest().getServiceRequestId());
                        serviceRequestFormLog.setIp(request.getRemoteAddr());
                        serviceRequestFormLog.setStatus(true);
                        serviceRequestFormLog.setMessage("  Failed TO Save The  Request... Operation Unsuccessful");
                        serviceRequestFormLogService.saveServiceRequestFormLog(serviceRequestFormLog);
                    }

                    res.setMessage(" Failed TO Save The Request Form... Operation Unsuccessful");
                    res.setStatus(false);
                    return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
                }
            }

        }

    }

    @Override
    public ResponseEntity<?> addIdentification(IdentificationAddDTO identificationAddDTO){
        Optional<CustomerServiceRequest> optional = customerServiceRequestRepository.findById(identificationAddDTO.getCustomerServiceRequestId());
        if(!optional.isPresent()){
            res.setMessage(" No Data Found To Complete The Request");
            res.setStatus(false);
            return new ResponseEntity<>(res, HttpStatus.NO_CONTENT);
        }
        int serviceRequestId = optional.get().getServiceRequest().getDigiFormId();
        if (serviceRequestId != ServiceRequestIdConfig.CHANGE_NIC_PASPORT_NO) {
            res.setMessage("Invalid Request");
            res.setStatus(false);
            return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
        }
        IdentificationForm identificationForm = new IdentificationForm();

        Optional<IdentificationForm> optionalForm = changeIdentificationFormRepository.getFormFromCSR(identificationAddDTO.getCustomerServiceRequestId());
        if (optionalForm.isPresent()) {
            identificationForm.setChangeIdentificationFormId(optionalForm.get().getChangeIdentificationFormId());
        }
        identificationForm.setIdentification(identificationAddDTO.getIdentification());
        identificationForm.setCustomerServiceRequest(optional.get());
        try{
            identificationForm = changeIdentificationFormRepository.save(identificationForm);
            return new ResponseEntity<>(identificationForm, HttpStatus.CREATED);
        } catch (Exception e) {
            res.setMessage("Something went wrong");
            res.setStatus(false);
            return new ResponseEntity<>(res, HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

    @Override
    public ResponseEntity<?> UpdateContactDetails(ContactDetailsDTO contactDetailsDTO, HttpServletRequest request) {
        Optional<CustomerServiceRequest> optional = customerServiceRequestRepository.findById(contactDetailsDTO.getCustomerServiceRequestId());
        if (!optional.isPresent()) {
            res.setMessage(" No Data Found To Complete The Request");
            res.setStatus(false);
            return new ResponseEntity<>(res, HttpStatus.NO_CONTENT);
        }
        int serviceRequestId = optional.get().getServiceRequest().getDigiFormId();
        if (serviceRequestId != ServiceRequestIdConfig.CHANGE_OF_TELEPHONE_NO) {
            res.setMessage("Invalid Request");
            res.setStatus(false);
            return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
        } else {

            CustomerServiceRequest customerServiceRequest = optional.get();
            ContactDetails contactDetails = new ContactDetails();
            Optional<ContactDetails> details = contactDetailsRepository.getFormFromCSR(contactDetailsDTO.getCustomerServiceRequestId());
            if (details.isPresent()) {
                contactDetails.setContactDetailsId(details.get().getContactDetailsId());
            }

            contactDetails.setMobileNumber(contactDetailsDTO.getMobileNumber());
            contactDetails.setOfficeNumber(contactDetailsDTO.getOfficeNumber());
            contactDetails.setResidenceNumber(contactDetailsDTO.getResidenceNumber());
            contactDetails.setEmail(contactDetailsDTO.getEmail());
            contactDetails.setCustomerServiceRequest(customerServiceRequest);

            if (contactDetailsRepository.save(contactDetails) != null) {

                serviceRequestCustomerLog.setDate(java.util.Calendar.getInstance().getTime());
                serviceRequestCustomerLog.setIdentification(customerServiceRequest.getCustomer().getIdentification());
                serviceRequestCustomerLog.setIp(request.getRemoteAddr());
                serviceRequestCustomerLog.setMessage("Request Form Successfully Saved To The System");
                boolean result = serviceRequestCustomerLogService.saveServiceRequestCustomerLog(serviceRequestCustomerLog);

                if (result) {
                    serviceRequestFormLog.setDigiFormId(customerServiceRequest.getServiceRequest().getDigiFormId());
                    serviceRequestFormLog.setCustomerId(customerServiceRequest.getCustomer().getCustomerId());
                    serviceRequestFormLog.setDate(java.util.Calendar.getInstance().getTime());
                    serviceRequestFormLog.setFromId(customerServiceRequest.getServiceRequest().getServiceRequestId());
                    serviceRequestFormLog.setIp(request.getRemoteAddr());
                    serviceRequestFormLog.setStatus(true);
                    serviceRequestFormLog.setMessage(" Request Form Successfully Saved To The  System ");
                    serviceRequestFormLogService.saveServiceRequestFormLog(serviceRequestFormLog);
                }

                res.setMessage(" Request Form Successfully Saved To The System");
                res.setStatus(true);
                return new ResponseEntity<>(res, HttpStatus.CREATED);
            } else {

                serviceRequestCustomerLog.setDate(java.util.Calendar.getInstance().getTime());
                serviceRequestCustomerLog.setIdentification(customerServiceRequest.getCustomer().getIdentification());
                serviceRequestCustomerLog.setIp(request.getRemoteAddr());
                serviceRequestCustomerLog.setMessage("Failed TO Save The Request... Operation Unsuccessful");
                boolean result = serviceRequestCustomerLogService.saveServiceRequestCustomerLog(serviceRequestCustomerLog);

                if (result) {
                    serviceRequestFormLog.setDigiFormId(customerServiceRequest.getServiceRequest().getDigiFormId());
                    serviceRequestFormLog.setCustomerId(customerServiceRequest.getCustomer().getCustomerId());
                    serviceRequestFormLog.setDate(java.util.Calendar.getInstance().getTime());
                    serviceRequestFormLog.setFromId(customerServiceRequest.getServiceRequest().getServiceRequestId());
                    serviceRequestFormLog.setIp(request.getRemoteAddr());
                    serviceRequestFormLog.setStatus(true);
                    serviceRequestFormLog.setMessage(" Failed TO Save The  Request... Operation Unsuccessful ");
                    serviceRequestFormLogService.saveServiceRequestFormLog(serviceRequestFormLog);
                }

                res.setMessage(" Failed TO Save The Request Form");
                res.setStatus(false);
                return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
            }
        }
    }
}
