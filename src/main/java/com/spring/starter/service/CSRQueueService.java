package com.spring.starter.service;

import org.springframework.http.ResponseEntity;

public interface CSRQueueService {

    public ResponseEntity<?> genarateNormalQueuNumber(int customerServiceRequestId);

    public ResponseEntity<?> getCSRQueueList();

    public ResponseEntity<?> getPendingQueue();

    public ResponseEntity<?> getHoldQueueList();

    public ResponseEntity<?> holdAQueueNumber(String queueNumber);

    public ResponseEntity<?> completeAQeueueNumber(String queueNumber);

    public ResponseEntity<?> completedQueueList();

    public ResponseEntity<?> continueAHoldQueueNumber(String queueNumber);

    public ResponseEntity<?> issueAPriorityToken(int customerServiceRequestId);

    public ResponseEntity<?> issueARedundentToken(int customerServiceRequestId);

}
