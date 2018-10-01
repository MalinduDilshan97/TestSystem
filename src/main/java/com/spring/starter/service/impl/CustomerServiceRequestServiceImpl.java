package com.spring.starter.service.impl;

import com.spring.starter.DTO.ContactDetailsDTO;
import com.spring.starter.DTO.IdentificationFormDTO;
import com.spring.starter.Repository.ChangeIdentificationFormRepository;
import com.spring.starter.Repository.ContactDetailsRepository;
import com.spring.starter.Repository.CustomerServiceRequestRepository;
import com.spring.starter.configuration.ServiceRequestIdConfig;
import com.spring.starter.model.ContactDetails;
import com.spring.starter.model.CustomerServiceRequest;
import com.spring.starter.model.IdentificationForm;
import com.spring.starter.model.ResponseModel;
import com.spring.starter.service.CustomerServiceRequestService;
import com.spring.starter.util.FileStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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
    private FileStorage fileStorage;
    private ResponseModel res = new ResponseModel();

    @Override
    public ResponseEntity<?> changeIdentificationDetails(IdentificationFormDTO identificationFormDTO) {
        ResponseModel responsemodel = new ResponseModel();
        Optional<CustomerServiceRequest> optional=customerServiceRequestRepository.findById(identificationFormDTO.getCustomerServiceRequestId());

        if (!optional.isPresent()){
            res.setMessage(" No Data Found To Complete The Request");
            res.setStatus(false);

            return new ResponseEntity<>(res, HttpStatus.NOT_FOUND);
        }
        int serviceRequestId = serviceRequestId = optional.get().getServiceRequest().getDigiFormId();
        if(serviceRequestId != ServiceRequestIdConfig.CHANGE_NIC_PASPORT_NO){
            responsemodel.setMessage("Invalied Request");
            responsemodel.setStatus(false);
            return new ResponseEntity<>(responsemodel, HttpStatus.BAD_REQUEST);
        } else{

            CustomerServiceRequest customerServiceRequest=optional.get();

//          creating a new Path
                String location=("/"+optional.get().getCustomerServiceRequestId()+"");
//          Saving and getting storage url
                String url= fileStorage.fileSave(identificationFormDTO.getFile(),location);
//          Checking Is File Saved ?
                if (url.equals("Failed")) {
                    res.setMessage(" Failed To Upload File");
                    res.setStatus(false);
                    return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
                }else {

                    IdentificationForm identificationForm=new IdentificationForm(0,identificationFormDTO.getIdentification(),
                            url,customerServiceRequest);

                    if (changeIdentificationFormRepository.save(identificationForm)!=null){
                        res.setMessage(" Request Form Successfully Saved To The System");
                        res.setStatus(true);
                        return new ResponseEntity<>(res, HttpStatus.CREATED);
                    }else{
                        res.setMessage(" Failed TO Save The Request Form... Operation Unsuccessful");
                        res.setStatus(false);
                        return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
                    }
            }

        }

    }

    @Override
    public ResponseEntity<?> UpdateContactDetails(ContactDetailsDTO contactDetailsDTO) {
        Optional<CustomerServiceRequest> optional=customerServiceRequestRepository.findById(contactDetailsDTO.getCustomerServiceRequestId());
        if (!optional.isPresent()){
            res.setMessage(" No Data Found To Complete The Request");
            res.setStatus(false);
            return new ResponseEntity<>(res, HttpStatus.NOT_FOUND);
        }
        int serviceRequestId = optional.get().getServiceRequest().getDigiFormId();
        if(serviceRequestId != ServiceRequestIdConfig.CHANGE_OF_TELEPHONE_NO){
            res.setMessage("Invalied Request");
            res.setStatus(false);
            return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
        }
        else{

            CustomerServiceRequest customerServiceRequest=optional.get();

            ContactDetails contactDetails=new ContactDetails();
            contactDetails.setContactDetailsId(0);
            contactDetails.setMobileNumber(contactDetailsDTO.getMobileNumber());
            contactDetails.setOfficeNumber(contactDetailsDTO.getOfficeNumber());
            contactDetails.setResidenceNumber(contactDetailsDTO.getResidenceNumber());
            contactDetails.setEmail(contactDetailsDTO.getEmail());
            contactDetails.setCustomerServiceRequest(customerServiceRequest);

            if (contactDetailsRepository.save(contactDetails)!=null){
                res.setMessage(" Request Form Successfully Saved To The System");
                res.setStatus(true);
                return new ResponseEntity<>(res, HttpStatus.CREATED);
            }else{
                res.setMessage(" Failed TO Save The Request Form");
                res.setStatus(false);
                return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
            }
        }
    }
}
