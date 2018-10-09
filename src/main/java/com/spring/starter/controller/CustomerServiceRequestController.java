package com.spring.starter.controller;

import com.spring.starter.DTO.ContactDetailsDTO;
import com.spring.starter.DTO.IdentificationAddDTO;
import com.spring.starter.DTO.IdentificationFormDTO;
import com.spring.starter.service.CustomerServiceRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/CustomerServiceRequest")
public class CustomerServiceRequestController {

    @Autowired
    private CustomerServiceRequestService customerServiceRequestService;

    @PostMapping("/addIdentification")
    public ResponseEntity<?> addIdentification(@RequestBody IdentificationAddDTO identificationAddDTO){
       return customerServiceRequestService.addIdentification(identificationAddDTO);
        //return new ResponseEntity<>(identificationAddDTO,HttpStatus.OK);
    }

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

    @GetMapping
    public ResponseEntity<?> test()
    {
        ContactDetailsDTO contactDetailsDTO = new ContactDetailsDTO();
        contactDetailsDTO.setMobileNumber("0710873073");
        contactDetailsDTO.setResidenceNumber("0342252011");
        contactDetailsDTO.setOfficeNumber("0342252218");
        contactDetailsDTO.setEmail("lakith1995@gmail.com");
        contactDetailsDTO.setCustomerServiceRequestId(15);

        return new ResponseEntity<>(contactDetailsDTO,HttpStatus.OK);
    }

}
