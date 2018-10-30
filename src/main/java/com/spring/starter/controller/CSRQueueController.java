package com.spring.starter.controller;

import com.spring.starter.service.CSRQueueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/CSR-Queue")
public class CSRQueueController {

    @Autowired
    CSRQueueService csrQueueService;

    @GetMapping("/genarate-token")
    public ResponseEntity<?> genarateNewGenaralToken(@RequestParam(name="serviceRequestCustomerId")int serviceRequestCustomerId){
        return csrQueueService.genarateNormalQueuNumber(serviceRequestCustomerId);
    }

    @GetMapping("/get-queue-list")
    public ResponseEntity<?> getQueuelist(){
        return csrQueueService.getCSRQueueList();
    }

    @GetMapping("/get-hold-queue-list")
    public ResponseEntity<?> getHoldQueueList(){
        return csrQueueService.getHoldQueueList();
    }

    @GetMapping("/get-pending-queue-list")
    public ResponseEntity<?> getPendingQueueList(){
        return csrQueueService.getPendingQueue();
    }

    @GetMapping("/hold-a-queue-number")
    public ResponseEntity<?> holdAQueueNumber(@RequestParam(name="queueNumber") String queueNumber){
        return csrQueueService.holdAQueueNumber(queueNumber);
    }

    @GetMapping("/complete-a-queue-number")
    public ResponseEntity<?> completeAQueueNumber(@RequestParam(name="queueNumber") String queueNumber) {
        return csrQueueService.completeAQeueueNumber(queueNumber);
    }

    @GetMapping("/get-completed-queue-list")
    public ResponseEntity<?> getCompltetedQueueList() {
        return csrQueueService.completedQueueList();
    }

    @GetMapping("/continue-a-hold-token")
    public ResponseEntity<?> continueToken(@RequestParam(name="queueNumber") String queueNumber){
        return csrQueueService.continueAHoldQueueNumber(queueNumber);
    }

    @GetMapping("/genarate-an-special-queue-number")
    public ResponseEntity<?> genarateSpecialToken(@RequestParam(name="serviceRequestCustomerId")int serviceRequestCustomerId){
        return csrQueueService.issueAPriorityToken(serviceRequestCustomerId);
    }

    @GetMapping("/genarate-redundent-token")
    public ResponseEntity<?> genarateRedundentToken(@RequestParam(name="serviceRequestCustomerId")int serviceRequestCustomerId){
        return csrQueueService.issueARedundentToken(serviceRequestCustomerId);
    }
}
