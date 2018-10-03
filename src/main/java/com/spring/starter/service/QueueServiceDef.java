package com.spring.starter.service;

import java.util.Optional;

import org.springframework.http.ResponseEntity;

import com.spring.starter.DTO.NewServiceDTO;
import com.spring.starter.DTO.QueueDTO;
import com.spring.starter.model.QueueService;

public interface QueueServiceDef {

    public ResponseEntity<?> addANewQueueNumber(QueueDTO queueDTO);

    public ResponseEntity<?> addNewServiceToExistingQueueNumber(NewServiceDTO newServiceDTO, int queueNumber);

    public Optional<QueueService> getAllmagulak();
}
