package com.spring.starter.controller;

import com.spring.starter.DTO.ContactDetailsDTO;
import com.spring.starter.DTO.IdentificationFormDTO;
import com.spring.starter.service.CustomerServiceRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/CustomerServiceRequest")
public class CustomerServiceRequestController {

    @Autowired
    private CustomerServiceRequestService customerServiceRequestService;

    @PostMapping("/identification-change")
    public ResponseEntity<?> IdentificationsChange(HttpServletRequest request,
                                                   @RequestParam MultipartFile file,
                                                   @RequestParam String identification,
                                                   @RequestParam int customerServiceRequestId) {

        IdentificationFormDTO identificationFormDTO = new IdentificationFormDTO(identification, file, customerServiceRequestId);
        return customerServiceRequestService.changeIdentificationDetails(identificationFormDTO,request);
    }

    @PutMapping("/identification-change")
    public ResponseEntity<?> updateIdentificationsChange(HttpServletRequest request,
                                                         @RequestParam MultipartFile file,
                                                         @RequestParam String identification,
                                                         @RequestParam int customerServiceRequestId) {

        IdentificationFormDTO identificationFormDTO = new IdentificationFormDTO(identification, file, customerServiceRequestId);
        return customerServiceRequestService.changeIdentificationDetails(identificationFormDTO,request);
    }

    @PostMapping("/contacts-change")
    public ResponseEntity<?> changeContactDetails(@RequestBody ContactDetailsDTO contactDetailsDTO,HttpServletRequest request) {
        return customerServiceRequestService.UpdateContactDetails(contactDetailsDTO,request);
    }

    @PutMapping("/contacts-change")
    public ResponseEntity<?> updateChangeContactDetails(@RequestBody ContactDetailsDTO contactDetailsDTO,HttpServletRequest request) {
        return customerServiceRequestService.UpdateContactDetails(contactDetailsDTO,request);
    }

}
