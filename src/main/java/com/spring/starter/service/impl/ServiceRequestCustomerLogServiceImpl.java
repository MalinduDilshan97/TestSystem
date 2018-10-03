package com.spring.starter.service.impl;

import com.spring.starter.Repository.ServiceRequestCustomerLogRepository;
import com.spring.starter.model.ServiceRequestCustomerLog;
import com.spring.starter.service.ServiceRequestCustomerLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class ServiceRequestCustomerLogServiceImpl implements ServiceRequestCustomerLogService {

    @Autowired
    private ServiceRequestCustomerLogRepository serviceRequestCustomerLogRepository;

    @Override
    public boolean saveServiceRequestCustomerLog(ServiceRequestCustomerLog serviceRequestCustomerLog) {
        return serviceRequestCustomerLogRepository.save(serviceRequestCustomerLog)!=null;
    }
}
