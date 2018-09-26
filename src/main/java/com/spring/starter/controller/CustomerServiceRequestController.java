package com.spring.starter.controller;

import com.spring.starter.DTO.ContactDetailsDTO;
import com.spring.starter.DTO.IdentificationFormDTO;
import com.spring.starter.service.CustomerServiceRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/CustomerServiceRequest")
public class CustomerServiceRequestController {

    @Autowired
    private CustomerServiceRequestService customerServiceRequestService;

    @PostMapping("/update/identification")
    public ResponseEntity<?> updateIdentifications(@RequestParam MultipartFile file,
                                                   @RequestParam String identification,
                                                   @RequestParam int customerServiceRequestId) {

        IdentificationFormDTO identificationFormDTO = new IdentificationFormDTO(identification, file, customerServiceRequestId);
        return customerServiceRequestService.changeIdentificationDetails(identificationFormDTO);
    }

    @PostMapping("/update/contacts")
    public ResponseEntity<?> changeContactDetails(@RequestBody ContactDetailsDTO contactDetailsDTO){
        return customerServiceRequestService.UpdateContactDetails(contactDetailsDTO);
    }

}
