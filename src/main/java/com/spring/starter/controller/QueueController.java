package com.spring.starter.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.starter.DTO.NewServiceDTO;
import com.spring.starter.DTO.QueueDTO;
import com.spring.starter.model.QueueService;
import com.spring.starter.service.QueueServiceDef;

@RestController
@RequestMapping("/queue")
public class QueueController {

	@Autowired
	QueueServiceDef queueServiceDef;
	
	@PostMapping("/addANewNumber")
	public ResponseEntity<?> addANewQueueNumber(@RequestBody QueueDTO queueDTO)
	{
		//return new ResponseEntity<>(queueDTO,HttpStatus.OK);
		return queueServiceDef.addANewQueueNumber(queueDTO);
	}
	
	@PostMapping("/addNewServiceToQueue/{queueNumber}")
	public ResponseEntity<?> addNewServiceToAnExistingQueue(@RequestBody NewServiceDTO serviceDTO,@PathVariable int queueNumber)
	{
		return queueServiceDef.addNewServiceToExistingQueueNumber(serviceDTO, queueNumber);
	}
	
	@GetMapping
	public Optional<QueueService> testing()
	{
		return queueServiceDef.getAllmagulak();
	}
}
