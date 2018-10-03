package com.spring.starter.controller;

import java.security.Principal;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.spring.starter.DTO.SignatureDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.spring.starter.DTO.CustomerDTO;
import com.spring.starter.DTO.CustomerRequestDTO;
import com.spring.starter.model.ChangePermanentMail;
import com.spring.starter.model.Customer;
import com.spring.starter.model.ServiceRequest;
import com.spring.starter.service.ServiceRequestService;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/serviceRequest")
public class ServiceRequestController {

    @Autowired
    ServiceRequestService serviceRequestService;

    @PostMapping("/addNewBankService")
    public ResponseEntity<?> addNewServiceRequest(@RequestBody @Valid ServiceRequest serviceRequest) {
        return serviceRequestService.addNewServiceRequest(serviceRequest);
    }

    @GetMapping("/getBankServices")
    public ResponseEntity<?> getBankServic() {
        return serviceRequestService.getBankServices();
    }

    @PostMapping("/addNewCustomer")
    public ResponseEntity<?> addANewCustomer(@RequestBody @Valid CustomerDTO customerDTO) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        return serviceRequestService.addNewCustomer(customerDTO,request);
    }

    @PostMapping("/addNewServiceToACustomer")
    public ResponseEntity<?> addNewServiceToACustomer(@RequestBody CustomerRequestDTO customerRequestDTO) {
        return serviceRequestService.addAServiceToACustomer(customerRequestDTO.getCutomerId(), customerRequestDTO.getServiceRequestId());
    }

    @GetMapping("/getcustomer")
    public Optional<Customer> getCust() {
        return serviceRequestService.getCustomer();
    }

    @GetMapping("/getServicesOfACustomerByDate")
    public ResponseEntity<?> getCustomerService(@RequestParam(name = "customerId") int customerId, @RequestParam("date") String date) {
        return serviceRequestService.getAllServiceRequests(customerId, date);
    }

    @GetMapping("/getAllCustomerRequests")
    public ResponseEntity<?> getAllCustomerDetails(@RequestParam(name = "customerId") int customerId) {
        return serviceRequestService.getAllCustomerRequests(customerId);
    }

    @GetMapping("/getAllCustomerDataWithRequests")
    public ResponseEntity<?> getAllCustomerRequestsWithCustomerDetails(@RequestParam(name = "customerId") int customerId) {
        return serviceRequestService.getCustomerDetailsWithServiceRequests(customerId);
    }

    @GetMapping("/completeACustomerRequest")
    public ResponseEntity<?> completeARequest(Principal principal, @RequestParam(name = "requestId") int requestId) {
        return serviceRequestService.completeARequest(principal, requestId);
    }

    @GetMapping("/addAStaffHandled")
    public ResponseEntity<?> addAStaffHandled(Principal principal, @RequestParam(name = "requestId") int requestId) {
        return serviceRequestService.addAStaffHandled(principal, requestId);
    }

    @GetMapping("/completeAllCustomerRequests")
    public ResponseEntity<?> completeAllRequests(Principal principal,@RequestParam(name = "customerId") int customerId){
        return serviceRequestService.completeAllCustomerRequests(principal,customerId);
    }

    @GetMapping("/getServiceRequestForm")
    public ResponseEntity<?> getServiceRequestForm(@RequestParam(name = "requestId") int requestId) {
        return serviceRequestService.getServiceRequestForm(requestId);
    }

    @GetMapping("/test")
    public ResponseEntity<?> testModel() {
        ChangePermanentMail changePermanentMail = new ChangePermanentMail();

        changePermanentMail.setNewPermanentAddress("thilakavilla,Thutthiripitiya,Halthota");
        changePermanentMail.setCity("Bandaragama");
        changePermanentMail.setPostalCode("kt12345");
        changePermanentMail.setStateOrProvince("Kaluthara");
        changePermanentMail.setCountry("SriLanka");
        changePermanentMail.setIssuingAuthority("issuingAuthority");
        changePermanentMail.setPlaceOfIssue("Kaluthara");

        return new ResponseEntity<>(changePermanentMail, HttpStatus.OK);
    }

    @PutMapping("addSignature")
    public ResponseEntity<?> uploadSignature(@RequestParam MultipartFile file,
                                             @RequestParam int customerServiceRequestId) {
        SignatureDTO signatureDTO = new SignatureDTO(customerServiceRequestId, file);
        return serviceRequestService.saveSignature(signatureDTO);
    }
}
