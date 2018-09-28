package com.spring.starter.service;

import com.spring.starter.model.OtherServiceRequest;
import org.springframework.http.ResponseEntity;

public interface OtherServiceReqService {

    ResponseEntity<?> addOtherRequest(OtherServiceRequest otherServiceRequest, int requestId);
}
