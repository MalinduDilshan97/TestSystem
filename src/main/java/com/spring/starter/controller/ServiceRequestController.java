package com.spring.starter.controller;

import java.security.Principal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.spring.starter.DTO.SignatureDTO;
import com.spring.starter.model.CustomerServiceRequest;
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
    public ResponseEntity<?> addANewCustomer(@RequestBody CustomerDTO customerDTO) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        return serviceRequestService.addNewCustomer(customerDTO,request);
        //return new ResponseEntity<>(customerDTO,HttpStatus.OK);
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

    @GetMapping("/getCustomerRequestsFilterByDate")
    public ResponseEntity<?> getCustomerDetailsFiilterBydate(@RequestParam("date") String date){
        return serviceRequestService.getCustomerDetailsByDate(date);
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

    @PostMapping("/tif-image")
    public ResponseEntity<?> savetif(@RequestParam  MultipartFile file,
                                     @RequestParam int serviceRequestCustomerId,
                                     @RequestParam int queueId) throws Exception {

        return serviceRequestService.saveTif(file,serviceRequestCustomerId,queueId);
    }

    @GetMapping("/tif-image")
    public ResponseEntity<?> getTif(@RequestParam String date) throws Exception {
        return serviceRequestService.getTifs(date);
    }

    @GetMapping("getDocumentTypes/{customerServiceRequestId}")
    public ResponseEntity<?> getDocuments(@PathVariable int customerServiceRequestId) {
        return serviceRequestService.getFIleTypes(customerServiceRequestId);
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

        List<String> accountNo = new ArrayList<>();
        accountNo.add("2131232132");
        accountNo.add("2131232124");

        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setIdentification("1232132132");
        customerDTO.setMobileNo("0710873073");
        customerDTO.setName("lakithMuthugala");
        customerDTO.setAccountNos(accountNo);

        return new ResponseEntity<>(customerDTO, HttpStatus.OK);
    }

    @PutMapping("addSignature")
    public ResponseEntity<?> uploadSignature(@RequestParam MultipartFile file,
                                             @RequestParam int customerServiceRequestId) {
        SignatureDTO signatureDTO = new SignatureDTO(customerServiceRequestId, file);
        return serviceRequestService.saveSignature(signatureDTO);
    }
}
