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
import javafx.geometry.Pos;
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
    public ResponseEntity<?> addNewServiceRequest(@RequestBody ServiceRequest serviceRequest) {
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
    public ResponseEntity<?> addNewServiceToACustomer(@RequestBody @Valid CustomerRequestDTO customerRequestDTO) {
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

    @GetMapping("/get-all-customer-requests-filter-by-reject")
    public ResponseEntity<?> getAllCustomerDetailsFilterByDate(@RequestParam(name = "customerId") int customerId) {
        return serviceRequestService.getAllCustomerRequestsWithourSoftReject(customerId);
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


    @PostMapping("/file-upload")
    public ResponseEntity<?> fileUpload(@RequestParam  MultipartFile file,
                                     @RequestParam int customerServiceRequestId,
                                     @RequestParam String fileType) throws Exception {

        return serviceRequestService.fileUploadForServiceRequest(fileType,file,customerServiceRequestId);
    }

    @PutMapping("addSignature")
    public ResponseEntity<?> uploadSignature(@RequestParam MultipartFile file,
                                             @RequestParam int customerServiceRequestId) {
        SignatureDTO signatureDTO = new SignatureDTO(customerServiceRequestId, file);
        return serviceRequestService.saveSignature(signatureDTO);
    }

    @PutMapping("/softReject")
    public ResponseEntity<?> softReject(@RequestParam(name = "requestId") int requestId){
        return serviceRequestService.rejectCustomerServiceRequest(requestId);
    }

    @GetMapping("/softReject")
    public ResponseEntity<?> getsoftRejectByDate(@RequestParam("date") String date){
        return serviceRequestService.getAllSoftRejectedRequestsByDate(date);
    }

    @GetMapping("/softReject-all")
    public ResponseEntity<?> getsoftRejectGetAll(){
        return serviceRequestService.getAllSoftRejectedRequests();
    }
}
