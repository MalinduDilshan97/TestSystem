package com.spring.starter.controller;


import com.spring.starter.model.OtherServiceRequest;
import com.spring.starter.service.OtherServiceReqService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("serviceRequest/otherRequest")
public class OtherRequestController {

    @Autowired
    OtherServiceReqService otherServiceReqService;

    @PostMapping
    public ResponseEntity<?> addOtherRequest(@RequestBody OtherServiceRequest otherServiceRequest, @RequestParam(name = "requestId") int requestId) {
        return otherServiceReqService.addOtherRequest(otherServiceRequest, requestId);
    }
}
