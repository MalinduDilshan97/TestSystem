package com.spring.starter.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.spring.starter.DTO.NewServiceDTO;
import com.spring.starter.DTO.QueueDTO;
import com.spring.starter.Repository.QueueNumRepository;
import com.spring.starter.Repository.QueueServiceRepository;
import com.spring.starter.Repository.ServiceRequestRepository;
import com.spring.starter.Repository.StaffUserRepository;
import com.spring.starter.model.QueueNum;
import com.spring.starter.model.QueueService;
import com.spring.starter.model.ResponseModel;
import com.spring.starter.model.ServiceRequest;
import com.spring.starter.model.StaffUser;
import com.spring.starter.service.QueueServiceDef;

@Service
@Transactional
public class QueueServiceImpl implements QueueServiceDef {

	@Autowired
	QueueNumRepository  queueNumRepository;
	
	@Autowired
	ServiceRequestRepository serviceRequestRepository; 
	
	@Autowired
	StaffUserRepository staffUserRepository; 
	
	@Autowired
	QueueServiceRepository  queueServiceRepository;
	
	
	
	@Override
	public ResponseEntity<?> addANewQueueNumber(QueueDTO queueDTO) {
		ResponseModel responsemodel = new ResponseModel();
		QueueNum queueNum = new QueueNum();
		queueNum.setMobileNo(queueDTO.getMobileNo());
		queueNum.setName(queueDTO.getName());
		queueNum.setQueueDate(java.util.Calendar.getInstance().getTime());

		queueNum = queueNumRepository.save(queueNum);
		
		Optional<ServiceRequest> serviceRequestOpt = serviceRequestRepository.findById(queueDTO.getServiceRequestId());
		if(!serviceRequestOpt.isPresent()) {
			responsemodel.setMessage("Service Id Is Not A Valied One Plase Check And Try Again");
			responsemodel.setStatus(false);
			return new ResponseEntity<>(responsemodel,HttpStatus.BAD_REQUEST);
		}
		List<ServiceRequest> serviceRequests = new ArrayList<ServiceRequest>();
		serviceRequests.add(serviceRequestOpt.get());
		
		Optional<StaffUser> staffUserOpt = staffUserRepository.findById(1);
		if(!staffUserOpt.isPresent()) 
		{
			responsemodel.setMessage("Staff Id Is Not A Valied One Plase Check And Try Again");
			responsemodel.setStatus(false);
			return new ResponseEntity<>(responsemodel,HttpStatus.BAD_REQUEST);
		}
		
		QueueService service = new QueueService();
		service.setQueue(queueNum);
		service.setServiceRequest(serviceRequests);
		service.setTellerId(staffUserOpt.get());

		queueServiceRepository.save(service);
		responsemodel.setMessage("[\"Queue Number\":"+queueNum.getQueueNumId()+",\"Service Request\":"+serviceRequestOpt.get().getServiceRequestName()+",\"Teller name\":"+staffUserOpt.get().getName()+"]");
		responsemodel.setStatus(true);
		return new ResponseEntity<>(responsemodel,HttpStatus.OK);
	}
	
	public ResponseEntity<?> addNewServiceToExistingQueueNumber(NewServiceDTO newServiceDTO , int queueNumber)
	{
		ResponseModel responsemodel = new ResponseModel();
		Optional<ServiceRequest> serviceRequestOpt = serviceRequestRepository.findById(newServiceDTO.getServiceId());
		if(!serviceRequestOpt.isPresent()) {
			responsemodel.setMessage("Service Id Is Not A Valied One Plase Check And Try Again");
			responsemodel.setStatus(false);
			return new ResponseEntity<>(responsemodel,HttpStatus.BAD_REQUEST);
		}
		Optional<QueueNum> queueNumOpt  = queueNumRepository.findById(queueNumber);
		if(!queueNumOpt.isPresent()) 
		{
			responsemodel.setMessage("QueueNum is not available");
			responsemodel.setStatus(false);
			return new ResponseEntity<>(responsemodel,HttpStatus.BAD_REQUEST);
		}
		QueueService queueServiceOpt =  queueServiceRepository.getQueueService(queueNumOpt.get().getQueueNumId());
		/*if(!queueServiceOpt.isPresent()) 
		{
			responsemodel.setMessage("queueService is not available");
			responsemodel.setStatus(false);
			return new ResponseEntity<>(responsemodel,HttpStatus.BAD_REQUEST);
		}
		QueueService queueService = queueServiceOpt.get();
 */		
		List<ServiceRequest> serviceRequests = queueServiceOpt.getServiceRequest();
		serviceRequests.add(serviceRequestOpt.get());
		try {
			queueServiceOpt.setServiceRequest(serviceRequests);
			queueServiceRepository.save(queueServiceOpt);
			responsemodel.setMessage("Queue Service Updated Successfully");
			responsemodel.setStatus(true);
			return new ResponseEntity<>(responsemodel,HttpStatus.OK);
		} catch (Exception e) {
			responsemodel.setMessage("QueueService Update fail");
			responsemodel.setStatus(true);
			return new ResponseEntity<>(responsemodel,HttpStatus.SERVICE_UNAVAILABLE);
		}
	}

	@Override
	public Optional<QueueService> getAllmagulak() {
       return queueServiceRepository.findById(1);
	}

}
