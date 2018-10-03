package com.spring.starter.service.impl;

import com.spring.starter.Repository.ServiceRequestFormLogRepository;
import com.spring.starter.model.ServiceRequestFormLog;
import com.spring.starter.service.ServiceRequestFormLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class ServiceRequestFormLogServiceImpl implements ServiceRequestFormLogService {

    @Autowired
    private ServiceRequestFormLogRepository serviceRequestFormLogRepository;

    @Override
    public boolean saveServiceRequestFormLog(ServiceRequestFormLog serviceRequestFormLog) {
        return serviceRequestFormLogRepository.save(serviceRequestFormLog) != null;
    }
}
