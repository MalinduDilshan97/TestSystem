package com.spring.starter.service;

import com.spring.starter.DTO.ContactDetailsDTO;
import com.spring.starter.DTO.IdentificationFormDTO;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;

public interface CustomerServiceRequestService {

    public ResponseEntity<?> changeIdentificationDetails(IdentificationFormDTO identificationFormDTO, HttpServletRequest request);

    public ResponseEntity<?> UpdateContactDetails(ContactDetailsDTO contactDetailsDTO,HttpServletRequest request);

}
