package com.spring.starter.service;

import com.spring.starter.DTO.ContactDetailsDTO;
import com.spring.starter.DTO.IdentificationFormDTO;
import org.springframework.http.ResponseEntity;

public interface CustomerServiceRequestService {

    public ResponseEntity<?> changeIdentificationDetails(IdentificationFormDTO identificationFormDTO);

    public ResponseEntity<?> UpdateContactDetails(ContactDetailsDTO contactDetailsDTO);

}
